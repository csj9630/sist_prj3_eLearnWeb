$(function () {
  // 초기 챕터 목록 로딩
  loadChapterList();

  // 챕터 추가 버튼 이벤트
  $("#btnAdd").click(function () {
    saveChapter("/instructor/chapter/rest/addChapter");
  });

  // 챕터 수정 버튼 이벤트
  $("#btnUpdate").click(function () {
    saveChapter("/instructor/chapter/rest/modifyChapter");
  });

  // 챕터 삭제 버튼 이벤트
  $("#btnDelete").click(function () {
    deleteChapter();
  });
});

//챕터 목록 조회 (Ajax)
function loadChapterList() {
  const lectId = $("#lectId").val(); // HTML의 hidden input에서 lectId를 가져옴

  $.ajax({
    url: "/instructor/chapter/rest/chapterList",
    type: "GET",
    data: { lectId: lectId },
    success: function (list) {
      let html = "";
      if (list.length === 0) {
        html =
          '<div class="text-center text-muted py-4">등록된 챕터가 없습니다.</div>';
      } else {
        list.forEach(function (item) {
          html += `
                        <a href="javascript:void(0)" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center" onclick="loadChapterDetail('${item.chptrId}')">
                            <div>
                                <span class="badge bg-secondary me-2">${item.num}차시</span> 
                                <strong>${item.name}</strong>
                            </div>
                            <small class="text-muted">${item.lengthStr || "시간 미정"}</small>
                        </a>
                    `;
        });
      }
      $("#chapterListArea").html(html);
    },
  });
}

// 챕터 상세 조회 (Ajax) - 우측 폼에 바인딩
function loadChapterDetail(chptrId) {
  $.ajax({
    url: "/instructor/chapter/rest/chapterDetail",
    type: "GET",
    data: { chptrId: chptrId },
    success: function (data) {
      // 폼 데이터 세팅
      $("#chptrId").val(data.chptrId);
      $("#name").val(data.name);
      //$("#video").val(data.video);
      $("#length").val(data.length);
	  $('#lengthStrView').val(data.lengthStr);

      $("#uploadFile").val(""); // 기존 파일 input 초기화

	  //DB에 저장된 영상 ID가 있다면 완전한 URL로 조립해서 출력
	  if (data.video) {
          const fullUrl = `https://youtu.be/${data.video}`;
          $("#video").val(fullUrl);
          $("#video").trigger("input"); // 썸네일/미리보기 강제 실행
      } else {
          $("#video").val('');
          $("#videoPreview").removeClass("d-flex").addClass("d-none");
      }
  
	  //유튜브 썸네일/미리보기 폼 출력 
	  if (data.video) {
	      // 값이 세팅된 후 강제로 'input' 이벤트를 발생시켜 썸네일과 길이를 다시 불러오게 함
	      $('#video').trigger('input');
	  } else {
	      // 영상 링크가 없는 챕터라면 미리보기 영역을 숨김
	      $('#videoPreview').removeClass('d-flex').addClass('d-none');
	  }
	  
      // 기존 첨부파일 표시
      if (data.doc) {
        $("#currentFile")
          .text("기존 첨부파일: " + data.doc)
          .show();
      } else {
        $("#currentFile").hide();
      }

      // UI 상태 변경 (수정 모드)
      $("#formTitle").text("챕터 수정");
      $("#btnAdd").hide();
      $("#editButtons").show();
    },
  });
}

// 챕터 추가 및 수정 (Ajax) - FormData 사용
function saveChapter(url) {
  //console.log("비디오 링크 값: [" + $("#video").val() + "]");
  //console.log("챕터명 값: [" + $("#name").val() + "]");

  // 유효성 검사: 챕터명,영상링크 필수
  if (!$("#name").val().trim()) {
    alert("챕터명은 필수 입력 사항입니다.");
    $("#name").focus();
    return;
  }

  const videoInput = $("#video").val().trim();
  if (!videoInput) {
      alert("유튜브 영상 링크는 필수 입력 사항입니다.");
      $("#video").focus();
      return;
  }
  
  //입력된 링크에서 11자리 ID만 추출
  const videoId = getYouTubeId(videoInput);
  if (!videoId) {
      alert("유효한 유튜브 영상 링크가 아닙니다. 다시 확인해 주세요.");
      $("#video").focus();
      return;
  }

  //폼데이터 객체 생성
  const formData = new FormData($("#chapterForm")[0]);

  //폼데이터의 video 값 id만 있게 덮어쓰기
  formData.set('video', videoId);
  
  $.ajax({
    url: url,
    type: "POST",
    data: formData,
    processData: false, // 파일 업로드 시 필수
    contentType: false, // 파일 업로드 시 필수
    success: function (res) {
      if (res === "success") {
        alert("정상적으로 저장되었습니다.");
        resetForm();
        loadChapterList(); // 리스트 새로고침
      } else {
        alert("처리 중 오류가 발생했습니다: " + res);
      }
    },
    error: function (err) {
      alert("통신 오류가 발생했습니다.");
      console.log(err);
    },
  });
}

//챕터 삭제 (Ajax)
function deleteChapter() {
  if (!confirm("정말 이 챕터를 삭제하시겠습니까?")) return;

  const chptrId = $("#chptrId").val();

  $.ajax({
    url: "/instructor/chapter/rest/removeChapter",
    type: "POST",
    data: { chptrId: chptrId },
    success: function (res) {
      if (res === "success") {
        alert("삭제되었습니다.");
        resetForm();
        loadChapterList();
      } else {
        alert("삭제 실패: " + res);
      }
    },
  });
}

//폼 초기화 (새 챕터 추가 모드로 전환)
function resetForm() {
  $("#chapterForm")[0].reset();
  $("#chptrId").val(""); // chptrId 비우기 (Insert 모드)
  $("#currentFile").hide().text("");

  //유튜브 정보 초기화
  $('#videoPreview').removeClass('d-flex').addClass('d-none');
  $('#thumbImg').attr('src', '');
  $('#videoTitleText').text('제목을 불러오는 중...');
  $('#videoDurationText').text('길이 확인 중...');
  $('#lengthStrView').val('');
  
  $("#formTitle").text("새 챕터 추가");
  $("#btnAdd").show();
  $("#editButtons").hide();
}
// --------------------------------------------------------------
// 유튜브 링크 자동 + 썸네일 + 길이 추출

let hiddenPlayer; // 유튜브 플레이어 객체(안 보이게 잠깐 호출)


// 동적 이벤트 바인딩 (HTML 로딩 시점과 무관하게 무조건 작동)
$(document).on('input', '#video', function() {
    const url = $(this).val().trim();
    const videoId = getYouTubeId(url);

    if (videoId) {
        // 1. 썸네일 이미지 표시
        $('#thumbImg').attr('src', `https://img.youtube.com/vi/${videoId}/mqdefault.jpg`);
        // d-none을 빼고 d-flex를 넣어서 예쁘게 보여줌
        $('#videoPreview').removeClass('d-none').addClass('d-flex'); 
        $('#videoTitleText').text('제목을 불러오는 중...');
        $('#videoDurationText').text('길이 확인 중...');

        // 2. 영상 길이/제목 추출을 위해 보이지 않는 플레이어 로드
        loadVideoDuration(videoId);
    } else {
        // 유효하지 않은 링크면 미리보기 숨김
        $('#videoPreview').removeClass('d-flex').addClass('d-none');
        $('#length').val(''); // 길이 초기화
    }
});

// 유튜브 URL에서 고유 ID(11자리)만 추출하는 정규식 함수
function getYouTubeId(url) {
    const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
    const match = url.match(regExp);
    return (match && match[2].length === 11) ? match[2] : null;
}

// YouTube IFrame API를 이용해 영상 길이와 제목을 가져오는 함수
function loadVideoDuration(videoId) {
    if (hiddenPlayer) {
        hiddenPlayer.destroy();
    }

    // 크기를 1x1로 해서 유튜브 API가 정상 인식하도록 생성
    hiddenPlayer = new YT.Player('hiddenPlayer', {
        height: '1',
        width: '1',
        videoId: videoId,
        events: {
            'onReady': function(event) {
                // 영상 길이 추출 및 세팅
                const duration = Math.floor(event.target.getDuration());
                $('#length').val(duration);

				//영상 시간 변환.
				const h = Math.floor(duration / 3600); // 1시간 = 3600초
                const m = Math.floor((duration % 3600) / 60);
                const s = duration % 60;
				
				//60분이 넘으면 시간 추가 없으면 빼기.
				let timeString = "";
                if (h > 0) {
                    timeString = `길이: ${h}시간 ${m}분 ${s}초`;
                } else {
                    timeString = `길이: ${m}분 ${s}초`;
                }
                $('#videoDurationText').text(timeString);
				$('#lengthStrView').val(timeString);

                // 영상 제목 추출 및 화면 표시
                const videoData = event.target.getVideoData();
                if (videoData && videoData.title) {
                    $('#videoTitleText').text(videoData.title);
                } else {
                    $('#videoTitleText').text('알 수 없는 영상');
                }
                
                // 정보 추출 끝났으니 객체 소멸 (메모리 확보)
                setTimeout(() => hiddenPlayer.destroy(), 500); 
                
                // 새로운 껍데기 div 다시 만들어주기 (destroy 시 태그 자체가 날아가므로)
                $('#hiddenPlayerContainer').append('<div id="hiddenPlayer"></div>');
            },
            'onError': function(event) {
                $('#videoDurationText').text('영상 정보를 불러올 수 없습니다.');
                $('#videoTitleText').text('오류 발생');
            }
        }
    });
}
