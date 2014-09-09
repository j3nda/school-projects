function recognizeOrientation() {
    var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;

    if (!is_chrome) {
        $(window).bind('orientationchange', function() {
            if (window.orientation % 180 == 0) {
                $(document.body)
                    .css("-webkit-transform-origin", "")
                    .css("-webkit-transform", "");
            
            } else {
                if ( window.orientation > 0) { //clockwise
                    $(document.body)
                        .css("-webkit-transform-origin", "280px 190px")
                        .css("-webkit-transform",  "rotate(-90deg)");
            
                } else {
                    $(document.body)
                        .css("-webkit-transform-origin", "280px 190px")
                        .css("-webkit-transform", "rotate(90deg)"); 
                }
            }
        })
        .trigger('orientationchange');
    }
}


function assignTouchEvents() {
    //Assign handlers to the simple direction handlers.
    var swipeOptions = {
        swipe:swipe,
        threshold:0
    }

    $(function() {			
        //Enable swiping...
        $("#canvas").swipe( swipeOptions );	
    });

    //Swipe handlers.
    //The only arg passed is the original touch event object			
    function swipe(event, direction) {
        //swipe right movement
        if (direction == 'right') {
            if (snakeDirection != 'left') {
                moveRight();
            }
        }

        //swipe up movement
        if (direction == 'up') {
            if (snakeDirection != 'down') {
                moveUp();
            }
        }

        //swipe left movement
        if (direction == 'left') {
            if (snakeDirection != 'right') {
                moveLeft();
            }
        }

        //swipe down movement
        if (direction == 'down') {
            if (snakeDirection != 'up') {
                moveDown();
            }
        }
    }
}


function startSnakeGame() {
    canvas = document.getElementById('canvas');
    canvas.width  = 320;
    canvas.height = 360;

    if (canvas.getContext) {
        $("#main-menu-bg").show();
        $("#header").hide();
        $("#in-game-score").hide();
        $("#in-game-paused").hide();

        $("#pause-pause").hide();
        $("#pause-pause").hide();

        mainMenu();
        ctx = canvas.getContext('2d');
        this.gridSize = 20;
        startLength   = 3;
        startLives    = 3;
        startSpeed    = 140;
        startLevel    = 1;
		startScore    = 0;

        for (var i=0; i<startLives; i++) {
            $("#lives-container").append('<img class="live" id="live'+(i+1)+'" src="live.png" />');
        }
		notInitYet = true;

    } else {
        alert("Máš starej browser!");
    }
}


function mainMenu() {
    $("#main-menu").modal({
        opacity:65,
        overlayCss: {backgroundColor:"#000"},
        onClose: function() {
            $("#main-menu-bg").hide();

            $("#header").show();
            $("#gameover-content").hide();

			// levely
			// graficky had
			// ulozeni paused game
			
			// 21b
			
			
			if (!notInitYet) {
				updateLives();
				updateLevel();
				updateScore();
			}

            $("#in-game-score").show();
            $("#in-game-paused").hide();

            $("#pause-pause").show();
            $("#pause-play").hide();
            $("#level-container").show();
            $("#lives-container").show();

            $.modal.close(); // must call this!
        }, persist: true
    });
}


function hideAllElements() {
    $("#highscore-content").hide();
    $("#main-content").hide();
    $("#highscore-content").hide();
    $("#gameover-content").hide();
    $("#in-game-score").hide();
}


function highscore() {
	allowPressKeys = false;
    updateHighScoreTable();
    
    $("#highscore-content").hide();
    $("#main-content").hide();
    $("#highscore-content").show();
    $("#gameover-content").hide();
}


function backToMainContentMenu() {
    $("#highscore-content").hide();
    $("#pause-content").hide();
    $("#header").hide();
    $("#main-content").show();
    $("#main-menu-bg").show();
    $("#gameover-content").hide();
    $("#in-game-score").hide();
    $("#in-game-paused").hide();
//    $("#pause-pause").hide();
//    $("#pause-play").hide();    
}


function pauseMenu() {
    $("#main-content").hide();
    $("#highscore-content").hide();

    $("#in-game-score").hide();
    $("#in-game-paused").show();
    $("#pause-pause").hide();
    $("#pause-play").show();
    
    $("#pause-content").show();
    $("#gameover-content").hide();
    mainMenu();
}


function gameOverMenu() {
    $("#main-content").hide();
    $("#highscore-content").hide();
    $("#pause-content").hide();
    $("#gameover-content").show();
    mainMenu();
}


function startInitializeSnake() {
	snakeLevel     = startLevel;
	snakeLives     = startLives;
	snakeEatFood   = 0;
	snakeScore     = startScore;
}


function start(withInit) {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    
    snakeDirection = 'right'; // initial direction, not random
    snakePosition  = {'x': 4*gridSize, 'y': 4*gridSize}; // initial position, not random
    snakeBody      = [];
    snakeSpeed     = startSpeed;
	snakeLength    = startLength;
	
	if (withInit === true) {
		startInitializeSnake();
	}
    
    // random direction
    var randDir    = ['right', 'down', 'left', 'up'];
    snakeDirection = randDir[Math.floor(Math.random() * randDir.length)];
    
    // random position due snakeDirection
    this.currentPosition = snakePosition;
    switch(snakeDirection) {
        case 'right':
        case 'left':
            this.currentPosition.x = (Math.floor(Math.random() * ((canvas.width /gridSize) - snakeLength)) + Math.floor(snakeLength/2)) * gridSize;
            this.currentPosition.y = (Math.floor(Math.random() * ((canvas.height/gridSize) - 2)) + 1) * gridSize;            
            break;

        case 'up':
        case 'down':
            this.currentPosition.x = (Math.floor(Math.random() * ((canvas.width /gridSize) - 2)) + 1) * gridSize;            
            this.currentPosition.y = (Math.floor(Math.random() * ((canvas.height/gridSize) - snakeLength)) + Math.floor(snakeLength/2)) * gridSize;
            break;
    }
    
    updateLives();
    updateLevel();
    updateScore();
    

    makeFood();
    drawSnake();    
	
	if (withInit === true) {
		play();
	}
}


function pause() {
    clearInterval(interval);
    allowPressKeys = false;
}


function play() {
    interval = setInterval(moveSnake, snakeSpeed);
    allowPressKeys = true;
}


function restart() {
    pause();
    start(true);
}


document.onkeydown = function(event) {
    if (!allowPressKeys){
        return null;
    }

    var keyCode;

    if (event == null) {
        keyCode = window.event.keyCode;

    } else {
        keyCode = event.keyCode;
    }

    switch(keyCode) {
        case 37: // left arrow key
            if (snakeDirection != 'right') {
                moveLeft();
            }
            break;

        case 38: // up arrow key
            if (snakeDirection != 'down') {
                moveUp();
            }
            break;

        case 39: // right arrow key
            if (snakeDirection != 'left') {
                moveRight();
            }
            break;

        case 40: // down arrow key
            if (snakeDirection != 'up') {
                moveDown();
            }
            break;

        default:
            break;
    }
}


function makeFood() {
    foodPosition = [Math.floor(Math.random()*(canvas.width/gridSize))*gridSize, Math.floor(Math.random()*(canvas.height/gridSize))*gridSize];

    if (snakeBody.some(hasFood)) {
        makeFood();

    } else {
        drawFood(foodPosition[0], foodPosition[1], gridSize, gridSize);
    };
}


function hasFood(element, index, array) {
    return (element[0] == foodPosition[0] && element[1] == foodPosition[1]);
}


function biteMe(element, index, array) {
    return (element[0] == currentPosition['x'] && element[1] == currentPosition['y']);
}


function drawSnake() {

    if (snakeBody.some(biteMe)) {
        snakeDie();
    }

    snakeBody.push([currentPosition['x'], currentPosition['y']]);
    ctx.fillStyle = "#454545";
    ctx.fillRect(currentPosition['x'], currentPosition['y'], gridSize, gridSize);

    if (snakeBody.length > snakeLength) {
        var itemToRemove = snakeBody.shift();
        ctx.clearRect(itemToRemove[0], itemToRemove[1], gridSize, gridSize);
    }

    if (currentPosition['x'] == foodPosition['0'] && currentPosition['y'] == foodPosition['1']) {
        makeFood();
        snakeEatFood++;
        snakeLength++;
        updateScore();
        
        if (snakeEatFood >= getMaxFoodPerLevel()) {
            snakeGoesToNextLevel();
        }
    }
}


function drawFood(x, y, width, height) {
    ctx.fillStyle = "rgb(10,100,0)";
    ctx.fillRect(x, y, width, height);
}


function moveSnake() {
    switch(snakeDirection) {
        case 'up':
            moveUp();
            break;
            
        case 'down':
            moveDown();
            break;
            
        case 'left':
            moveLeft();
            break;
            
        case 'right':
            moveRight();
            break;		
    }
}


function leftPosition() {
    return currentPosition['x'] - gridSize;
}


function rightPosition() {
    return currentPosition['x'] + gridSize;
}


function upPosition() {
    return currentPosition['y'] - gridSize;
}


function downPosition() {
    return currentPosition['y'] + gridSize;
}


function moveUp() {
    if (upPosition() >= 0) {
        executeMove('up', 'y', upPosition());
        
    } else {
        whichWay('x');
    }
}


function moveDown() {
    if (downPosition() < canvas.height) {
        executeMove('down', 'y', downPosition());
        
    } else {
        whichWay('x');
    }
}


function moveLeft() {
    if (leftPosition() >= 0) {
        executeMove('left', 'x', leftPosition());
        
    } else {
        whichWay('y');
    }
}


function moveRight() {
    if (rightPosition() < canvas.width) {
        executeMove('right', 'x', rightPosition());
        
    } else {
        whichWay('y');
    }
}


function executeMove(dirValue, axisType, axisValue) {
    snakeDirection = dirValue;
    currentPosition[axisType] = axisValue;
    drawSnake();
}


function whichWay(axisType) {
    if (axisType == 'x') {
        a = (currentPosition['x'] > canvas.width / 2) ? moveLeft() : moveRight();
        
    } else {
        a = (currentPosition['y'] > canvas.height / 2) ? moveUp() : moveDown();
    }
}


function gameOver() {
    pause();
    updateGameoverScore();
    gameOverMenu();
    
    allowPressKeys = false;
    ctx.clearRect(0,0, canvas.width, canvas.height);
}


function updateScore() {
    var score = getActualScore();
//    document.getElementById('score').innerHTML = score;
    document.getElementById('score2').innerHTML = score;
}


function updateLevel() {
    document.getElementById('level').innerHTML = snakeLevel;
}


function updateLives() {
    $("#lives-container .live").each(function(index) {
        if ((index+1) < snakeLives) {
            $(this).removeClass("livesOpacity");

        } else {
            $(this).addClass("livesOpacity");
        }
    });
}


function updateGameoverScore() {
    var highScore  = localStorage.getItem("high-score");
    var playerName = localStorage.getItem("playerName");
    var score      = getActualScore();

    if (playerName == '' || playerName == null) {
        playerName = 'tamz1snake';
    }

    $('#snakePlayer').val(playerName);
    $('.current-score').html(score);

    if (score > highScore) {
        $("#your-score").hide();
        $("#your-record").show();

        $('.high-score').html(score);
        localStorage.setItem('high-score', score);
        
    } else {
        $("#your-score").show();
        $("#your-record").hide();        
        $('.high-score').html(highScore);
    }
}


function getActualScore() {
    return ((snakeLength - startLength)*10) + snakeScore;
}


function getActualPlayerName() {
    return $('#snakePlayer').val();
}


function getMaxFoodPerLevel() {
    return snakeLevel*2;
}


function snakeGoesToNextLevel() {
    
    
    snakeLevel++;
    snakeEatFood = 0;
    updateLevel();
    updateLives();
}


function snakeDie() {
//	pause();
//	snakeDieScreenBlink();
//	play();

	// TODO: snakeDieScreenBlink -> red(timeout)
//	snakeLength = Math.floor(snakeLength/2); // TODO: zbytel hada smazat + asi novy start
	
    snakeLives--;
    updateLives();
    if (snakeLives < 1) {
        snakeLives = 0;
        gameOver();
        return false;
    }
	snakeScore = Math.floor(getActualScore()/2);
	start();
}


function getHighScoresLimit() {
	return 7;
//    return 20;
}


function getDefaultHighScores() {
    return [
//        {'name': 'tamz1snake', 'score':  10, 'time': 1},
//        {'name': 'tamz1snake', 'score':  20, 'time': 2},
//        {'name': 'tamz1snake', 'score':  30, 'time': 3},
//        {'name': 'tamz1snake', 'score':  40, 'time': 4},
//        {'name': 'tamz1snake', 'score':  50, 'time': 5},
//        {'name': 'tamz1snake', 'score':  60, 'time': 6},
//        {'name': 'tamz1snake', 'score':  70, 'time': 7},
//        {'name': 'tamz1snake', 'score':  80, 'time': 8},
//        {'name': 'tamz1snake', 'score':  90, 'time': 9},
//        {'name': 'tamz1snake', 'score': 100, 'time': 10},
//        {'name': 'tamz1snake', 'score': 110, 'time': 11},
//        {'name': 'tamz1snake', 'score': 120, 'time': 12},
//        {'name': 'tamz1snake', 'score': 130, 'time': 13},
//        {'name': 'tamz1snake', 'score': 140, 'time': 14},
//        {'name': 'tamz1snake', 'score': 150, 'time': 15},
//        {'name': 'tamz1snake', 'score': 160, 'time': 16},
        {'name': 'tamz1snake', 'score': 170, 'time': 17},
        {'name': 'tamz1snake', 'score': 180, 'time': 18},
        {'name': 'tamz1snake', 'score': 190, 'time': 19},
        {'name': 'tamz1snake', 'score': 200, 'time': 20}
    ];
}


// politika
// stromy: historie
// stromy: kultury
// stromy: mentality
// stromy: vzdelanosti-filosofie-prirodnich-ved





function updateHighScores(addPlayer) {
    var topTenString = localStorage.getItem("highscores");
    var topTen       = (topTenString != '' && topTenString != null ? JSON.parse(topTenString) : []);
    var unixTime     = Math.round(+new Date()/1000);

    if (topTen == null || topTen.length == 0) {
        topTen = getDefaultHighScores();
    }

    if (addPlayer && getActualScore() > 0) {
        topTen.push({'name': getActualPlayerName(), 'score': getActualScore(), 'time': unixTime});
    }

    topTen.sort(function(a, b) {
        if (a.score == b.score) {
            if (a.time == b.time) {
                if (a.name == b.name) {
                    return 0;
                }
                return (a.name > b.name ? 1 : -1);
            }
            return (a.time > b.time ? -1 : 1);
        }
        return (a.score > b.score ? -1: 1); // 9999 to -1
    });

    var topTen2 = []
    for(var i = 0; i<topTen.length; i++) {
        if (i < getHighScoresLimit() && topTen[i].score > 0) {
			topTen[i].name.trim(); // TODO: trim nefunguje!!!
            topTen2.push(topTen[i]);
        }
    }
    topTen = topTen2;
//console.log(JSON.stringify(topTen2, null, 4));
    localStorage.setItem("highscores", JSON.stringify(topTen2));
}

function updatePlayerName(name) {
    if (name != '') {
        localStorage.setItem("playerName", name);
    }
}


function updateHighScoreTable() {
    updateHighScores(false);

    var topTenString = localStorage.getItem("highscores");
    var topTen       = (topTenString != '' && topTenString != null ? JSON.parse(topTenString) : []);   
    var content      = "";
	var clas         = ["", "", ""];
    
    $('#topten-content').empty();
    for(var i=0; i<topTen.length; i++) {
        if (i < getHighScoresLimit()) {
			clas = ["", "", ""]; // tr, td-score, td-name
			
			if (i == 0) {
				clas[0] = "first"; clas[1] = "firstScore"; clas[2] = "firstName";
				if (topTen[i].name.length >= 11) {
					topTen[i].name = topTen[i].name.substr(0, 11)+"..." // TODO: utf-8 3x tecky!!!
				 }
			
			} else if (i == 1) {
				clas[0] = "second"; clas[1] = "secondScore"; clas[2] = "secondName";
				if (topTen[i].name.length >= 13) {
					topTen[i].name = topTen[i].name.substr(0, 13)+"..." // TODO: utf-8 3x tecky!!!
				 }
				
			} else if (i == 2) {
				clas[0] = "third"; clas[1] = "thirdScore"; clas[2] = "thirdName";
				if (topTen[i].name.length >= 15) {
					topTen[i].name = topTen[i].name.substr(0, 15)+"..." // TODO: utf-8 3x tecky!!!
				 }
				 
			} else {
				if (topTen[i].name.length >= 17) {
					topTen[i].name = topTen[i].name.substr(0, 17)+"..." // TODO: utf-8 3x tecky!!!
				 }
			}
			
            content = content
						+ "<tr"+(clas[0] != "" ? " class=\""+clas[0]+"\" " : "")+">"
						+ "<td"+(clas[1] != "" ? " class=\""+clas[1]+"\" " : "")+">"
							+(topTen[i].score > 10000
								? (topTen[i].score > 100000
									? (topTen[i].score > 1000000
										? Math.round(topTen[i].score/100000)+"g"
										: Math.round(topTen[i].score/10000)+"m"
									  )
									: Math.round(topTen[i].score/1000)+"k"
								  )
								: topTen[i].score
							 )
							+"</td>"
						+ "<td"+(clas[2] != "" ? " class=\""+clas[2]+"\" " : "")+">"
							+topTen[i].name
							+"</td>"
						+ "</tr>";
        }
    }
    
    if (content != "") {
        content = "<div style=\"width:100%;height:135px;override:none;\"><table border=1>"+content+"</table></div>";

    } else {
        content = "<div style=\"width:100%;height:135px;text-align:center;\"><div style=\"padding-top:62px;\">Chybí data!</div></div>"
    }
    
    $('#topten-content').append(content);
	
}