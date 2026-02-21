//************* ThymeLeaf로 DB 값 저장 ** *****************
      //IDE에서 에러 표시 나서 주석 기법으로 변경
     // const videoJSONArr = /*[[${vdList}]]*/ "default_value";
     // const startChptrId = /*[[${startChptrId}]]*/ "default_id";
     // const exitUrl = /*[[@{/csj}]]*/ "/";

     // const currentUserId = /*[[${session.userId}]]*/ "noName"; //세션에서 아이디 저장.

     // console.log(videoJSONArr);

      //*********전역변수*****************
      var videoData; // 현재 화면에서 볼 영상정보.
      var currentIndex = 0; //vdList의 현재 index 번호
      var beforeIndex = 0; //vdList의 현재 index 번호
      var player;

      var lastTime = 0; // 현재 시청시간
      var maxTime = 0; //실제 시청시간-->영상 시청한 최대 위치로 변경.
      const interval = 1000; //반복 추적시간.

      //지정 시간마다 실제 시청 시간을 추척함.
      setInterval(() => {
        //player 객체가 멀쩡할 때만 작동.
        if (player && typeof player.getPlayerState === "function") {
          //현재 재생 시간보다 최대시간이 크면 갱신.
          var currentT = getTime();
          if (currentT > maxTime) {
            maxTime = currentT;
          } //if

          //영상 실제 시간에서 최대 시간으로 변경.
          // if (player.getPlayerState() === YT.PlayerState.PLAYING) {
          //   trackVideoTime(getTime());

          //   //테스트용.
          //   $("#lastTime").text(lastTime);
          //   $("#maxTime").text(maxTime);
          // }
        }
      }, interval);

      //페이지 벗어날 때 알림메시지(종료 외 방법으로)
      var checkUnload = true;
      $(window).on("beforeunload", function () {
        if (checkUnload) {
          return "종료 버튼 외 방법으로 페이지를 벗어나면  내용은 저장되지 않습니다.";
        }
      });

      //*********document.ready***********
      $(function () {
        //JSONArr에서 num 컬럼으로 videoJSON과 index번호 Setting
        VideoChapterReady(startChptrId, videoJSONArr);

        //테스트를 위해 일단 다 보여주면서 시작.
        $(".playerSection").addClass("loaded");
        //************ 버튼 할당 ******************
        $("#btnExit").click(function () {
          checkUnload = false; //알림 안뜨고 나가기.
          uploadRecord();
          location.href = exitUrl;
        }); //click
        $("#btnPrev").click(function () {
          uploadRecord();
          updateCurrentTimeOnTable(currentIndex);
          playVideoByIndex(currentIndex - 1);
        }); //click
        $("#btnNext").click(function () {
          uploadRecord();
          updateCurrentTimeOnTable(currentIndex);
          playVideoByIndex(currentIndex + 1);
        }); //click
      }); //document.ready

      //************ 함수 정의 ******************
	  
      //실제 시청시간을 추적.-> 이제 안 쓴다.
      /*
      function trackVideoTime(currentTime) {
        const playbackRate = player.getPlaybackRate(); // 현재 재생 속도
        const range = playbackRate + 0.5; //범위값

        const zeta = currentTime - lastTime;

        //0< (현재 시간 - 마지막 저장시간) < 1.2 일때만 정상 재생 인정.
        if (zeta > 0 && zeta < range) {
          maxTime += Math.floor(zeta / playbackRate);
          //maxTime++;
        } //if

        //마지막 저장시간 업데이트
        lastTime = currentTime;
      } //func
      */

      //초기 시작 때 사용.
      //num 컬럼으로 videoList에서 일치하는 videoJson 하나를 전역 저장.
      //currentIndex에 찾은 index 번호 전역 저장.
      function VideoChapterReady(targetId, jsonArr) {
        const foundIndex = jsonArr.findIndex(
          (item) => String(item.chptrId) == targetId,
        );
		

		
        if (foundIndex !== -1) {
          currentIndex = foundIndex;
        } else {
          currentIndex = 0; // 에러 방지용 기본값
        }
        videoData = jsonArr[currentIndex]; //index로 videoJson을 전역 저장.
        maxTime = videoData.actualTime || 0; //DB값 로드 :실제 시청시간, 없으면 0
        updateRowHighlight(currentIndex); //목록에서 현재 영상 하이라이트.
		if(confirm("시청이 완료된 강의입니다. 처음부터 시청하시겠습니까?")){
				videoData.progTime=0;
			}
			document.title = videoData.title; //브라우저 제목 변경
			$("#currentVideoTitle").text(videoData.title); //<h3> 텍스트 변경
      } //function

      //영상 바꿀 때 쓰는 함수.
      function playVideoByIndex(index) {
        if (index < 0) {
          alert("첫 번째 강의입니다.");
          return;
        }
        if (index >= videoJSONArr.length) {
          alert("마지막 강의입니다. 수고하셨습니다!");
          return;
        }

        videoData = videoJSONArr[index]; //전역 변수로 영상 정보 저장.

        //시청 완료한 영상일 때 시작시간 지정(state=2)
        let startTime = videoData.progTime;
        if (videoData.state == 2 && confirm("시청이 완료된 강의입니다. 처음부터 시청하시겠습니까?")) {
			
          startTime = 0; // 완료된 영상은 처음부터 재생
        } //if

        document.title = videoData.title; //브라우저 제목 변경
        $("#currentVideoTitle").text(videoData.title); //<h3> 텍스트 변경

        //변경 전 video 영상시간을 jsonArr에 갱신.
        videoJSONArr[currentIndex].progTime = getTime();

        //변경 전 video 실제 시청시간을 jsonArr에 갱신.
        videoJSONArr[currentIndex].actualTime = maxTime;

        //index 번호로 영상 재생 및 시간 지정
        loadVideo(videoData.videoUrl, startTime);

        //목록에서 현재 영상 하이라이트.
        updateRowHighlight(index);

        currentIndex = index; // 전역 변수로 현재 번호 저장.
        maxTime = videoData.actualTime; //실제 시청시간
      } //function

      //강의 목록에서 현재 영상에 하이라이트 효과.
      function updateRowHighlight(index) {
        const $targetRow = $("#row-" + index); // ID로 대상 행 선택
		const $container = $(".playlist-scroll");
		currentVideoTitle
        if ($targetRow.length > 0) {
          // 1. 해당 행에 Bootstrap 강조 클래스 추가
          // 2. 형제 요소(siblings)들에서는 해당 클래스 제거 (한 번에 처리)
          $targetRow
            .addClass("table-primary fw-bold active")
            .siblings()
            .removeClass("table-primary fw-bold active");
$("#currentVideoTitle").text
			//컨테이너 내부에서의 상대적 위치를 계산해서 스크롤
			const rowTop = $targetRow.position().top; // 컨테이너 상단으로부터의 거리
	        const containerHeight = $container.height();
	        const currentScroll = $container.scrollTop();	
			
          // 3. 선택된 행이 화면 중앙에 보이도록 스크롤 (jQuery 애니메이션 효과)
		  $container.stop().animate({
		              scrollTop: currentScroll + rowTop - (containerHeight / 2)
		          }, 500);
        }
      } //function

      //영상 기록을 ajax로 DB 저장.
      function uploadRecord() {
        console.log(player);

        const currentTime = getTime();
        const videoLength = getVideoLength();
        const actualTime = maxTime;

		
		
        const saveData = {
          userId: currentUserId,
          chptrId: videoData.chptrId || "unknown",
          progTime: currentTime || 0,
          videoLength: videoLength || 0,
          actualTime: actualTime || 0,
        };

        console.log(saveData);

        $.ajax({
          url: "/lecture/chapter/saveRecord",
          type: "POST",
          contentType: "application/json",
          data: JSON.stringify(saveData),
          error: function (xhr) {
            alert("시청 이력을 저장할 수 없습니다!");
          },
          success: function (response) {
            console.log(response);
          },
        }); //ajax
      } //function

      //강의 목록의 시청 시간을 갱신한다.
      function updateCurrentTimeOnTable(index) {
        const $targetRow = $("#row-" + index);
        if ($targetRow.length > 0) {
          $targetRow.find(".time-total").text(convertHms(getVideoLength()));
          $targetRow.find(".time-current").text(convertHms(getTime()));
        }
		
		/*// 🌟 리스트의 전체시간/현재시간 텍스트를 실제로 바꾸는 함수
		function updateListUI(index) {
		    const data = videoJSONArr[index];
		    const $row = $("#row-" + index);

		    if ($row.length > 0) {
		        // 데이터에서 꺼낸 시간을 포맷팅하여 텍스트 교체
		        $row.find(".time-total").text(formatTime(data.videoLength));
		        $row.find(".time-current").text(formatTime(data.progTime));
		    }
		}*/
      } //function

      //초 -> 시:분:초로 변환.
      function convertHms(seconds) {
        if (seconds < 61) {
          return "00:" + addZero(seconds);
        }
        // sec
        var hours = Math.floor(seconds / 3600);
        var mins = Math.floor((seconds - hours * 3600) / 60);
        var secs = seconds - hours * 3600 - mins * 60;
        if (hours > 0) {
          return addZero(hours) + ":" + addZero(mins) + ":" + addZero(secs);
        } else {
          return addZero(mins) + ":" + addZero(secs);
        }
        function addZero(num) {
          return (num < 10 ? "0" : "") + num;
        }
      } //function
	  
	  


      //************* Youtube API Setting *******************
      // 2. This code loads the IFrame Player API code asynchronously.
      var tag = document.createElement("script");

      tag.src = "https://www.youtube.com/iframe_api";
      var firstScriptTag = document.getElementsByTagName("script")[0];
      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

      // 3. This function creates an <iframe> (and YouTube player)
      //          after the API code downloads.

      function onYouTubeIframeAPIReady() {
        if (videoData && videoData.videoUrl) {
          player = new YT.Player("player", {
            height: "390",
            width: "640",
            videoId: videoData.videoUrl, //model에서 url 가져옴

            playerVars: {
              playsinline: 1,
              //'autoplay': 1,
              //'mute': 1, //소리 켜져있으면 브라우저에서 자동 재생 막음.
            },
            events: {
              onReady: onPlayerReady,
              //onStateChange: onPlayerStateChange,
            },
          });
        }
      }

      // 4. The API will call this function when the video player is ready.
      function onPlayerReady(event) {
        moveTime(videoData.progTime); //model값으로 시간 설정
        event.target.playVideo(); //영상 재생

        // 플레이어가 준비되었을 때 로딩 스피너 숨기고, 화면을 한번에 보여줌
        $("#loading").hide();
        $(".playerSection").addClass("loaded");
      } //func

      //***************동영상 함수**********************
      var done = false;
      function onPlayerStateChange(event) {
        // if (event.data == YT.PlayerState.PLAYING && !done) {
        //   setTimeout(stopVideo, 6000);
        //   done = true;
        // }
        if (event.data != YT.PlayerState.PLAYING) {
          lastTime = getTime();
        }
      }

      function stopVideo() {
        player.stopVideo();
      }

      //영상 음소거
      function muteVideo() {
        player.mute();
      }

      //현재 영상 시간 추출하기
      function getTime() {
		if(!player) {return 0};
        return Math.floor(player.getCurrentTime());
      } //

      //테스트용 시간 추출하기
      function getVideoLength() {
        return Math.floor(player.getDuration());
      } //

      //시간 이동
      function moveTime(sec) {
        if (sec == 0 || !sec) {
          sec = 0;
        }
        player.seekTo(sec, true);
      }

      //받은 id로 영상 교체 및 영상시간 지정(시간 없으면 0으로).
      function loadVideo(videoId, progTime) {
        if (progTime == 0 || !progTime) {
          progTime = 0;
        }
        player.loadVideoById(videoId, progTime);
      } //func