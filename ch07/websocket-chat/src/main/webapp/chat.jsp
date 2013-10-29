<!DOCTYPE html>
<meta charset="utf-8"/>
<head>
    <title>WebSocket Chat Server Example</title>
    <link rel="stylesheet" media="all" href="styles/chat.css" />

    <script src="scripts/jquery-1.9.1.js" ></script>

    <script language="javascript" type="text/javascript">
        var DELIMITER           = "::::"
        var LOGIN_REQUEST       = "LOGIN"
        var LOGOUT_REQUEST      = "LOGOUT"
        var SEND_MSG_REQUEST    = "SENDMSG"
        var MESSAGE_REPLY       = "MESSAGE_REPLY";
        var ERROR_REPLY         = "ERROR_REPLY";

        var wsUri = "ws://localhost:8080/mywebapp/chat";
        var websocket;
        var outputDiv;
        var messagesDiv
        var username = "(none)"

        function init() {
            outputDiv = $("#output");
            messagesDiv = $("#messages");
            openWebSocket();
        }

        function openWebSocket() {
            websocket = new WebSocket(wsUri);
            websocket.onopen = function (evt) {
                onOpen(evt)
            };
            websocket.onclose = function (evt) {
                onClose(evt)
            };
            websocket.onmessage = function (evt) {
                onMessage(evt)
            };
            websocket.onerror = function (evt) {
                onError(evt)
            };
        }

        function onOpen(evt) {
            writeToScreen("CONNECTED");
        }

        function onClose(evt) {
            writeToScreen("DISCONNECTED");
        }

        function onMessage(evt) {
            var tokens = evt.data.split( DELIMITER )
            // DEBUG: protocolMessage("tokens[0]="+tokens[0]+", tokens[1]="+tokens[1]+", tokens[2]="+tokens[2])
            var replyCode = tokens[0]
            var peerUsername = tokens[1]
            var text = tokens[2]
            if ( replyCode == MESSAGE_REPLY ) {
                writeToScreen('<b>'+peerUsername+':</b> &nbsp; <span style="color: blue;">'+text+'</span>');
            }
            else if ( replyCode == ERROR_REPLY ) {
                writeToScreen('<b>'+peerUsername+':</b> &nbsp; <span style="color: red;">'+text+'</span>');
            }
            else {
                writeToScreen('<span style="color: brown;"><b>UNKNOWN COMMAND:</b> &nbsp; user:'+peerUsername + ' '+ text + ' </span>');
            }
        }

        function onError(evt) {
            writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
        }

        function sendMessage(message) {
            var msg = SEND_MSG_REQUEST +
                    DELIMITER + username +
                    DELIMITER + message
            protocolMessage("Sending Message: " + msg);
            websocket.send(msg);
        }

        function login() {
            var msg = LOGIN_REQUEST +
                    DELIMITER + username
            protocolMessage("Sending Message: " + msg);
            websocket.send(msg);
        }

        function logout() {
            var msg = LOGOUT_REQUEST +
                    DELIMITER + username
            protocolMessage("Sending Message: " + msg);
            websocket.send(msg);
        }

        function writeToScreen(message) {
            var pre = document.createElement("p");
            pre.style.wordWrap = "break-word";
            pre.innerHTML = message;

            outputDiv.append(pre)
            outputDiv.animate( {
                scrollTop: outputDiv[0].scrollHeight
            }, 1000 )
        }

        function protocolMessage(message) {
            var d1 = new Date()
            var timestamp = d1.toDateString()+"-"+d1.toLocaleTimeString();
            var pre = document.createElement("p");
            pre.style.wordWrap = "break-word";
            pre.innerHTML = timestamp + " - " + message;
            messagesDiv.append(pre);
            messagesDiv.animate( {
                scrollTop: messagesDiv[0].scrollHeight
            }, 1000 )
        }

        $(document).ready( function() {
            init()
            $("#loginButton").click(function( evt ) {
                username = $("#username").val()
                login()
                $("#username").prop("disabled", "true");
            });
            $("#logoutButton").click(function( evt ) {
                username = $("#username").val()
                logout()
                $("#username").prop("disabled", "");
            });
            $("#messageText").keydown( function( evt ) {
                var keycode = (evt.keyCode ? evt.keyCode : evt.which);
                if ( keycode == 13 ) {
                    var messageText = $("#messageText")
                    sendMessage(messageText.val())
                    messageText.val("")
                }
            })
        } )
    </script>
</head>
<body>
<h2>Java EE 7 WebSocket Chat Server Example</h2>
<div id="login">
    Username: <input type="text" id="username" /> <br/>
    <input type="button" value="Login"  name="action" id="loginButton" />
    <input type="button" value="Logout" name="action" id="logoutButton" />
</div>
<div id="control">
    Enter your message: <br/>
    <input type="text" id="messageText" name="messageText" /> <br/>
    <br>
</div>
<div id="messages">
    <b>PROTOCOL MESSAGES:</b><br/>
</div>
<div id="output"></div>
</body>
</html>