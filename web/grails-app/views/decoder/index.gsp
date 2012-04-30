<html>
   <head>
      <title>Decode Encoded Messages</title>
      <meta name="layout" content="main"/>

      <r:require modules="bootstrap, jquery, prettify, jqueryClipboard"/>

     <script type="text/javascript">
      $(document).ready(function(){

        /** Register the click of the button to encode the message. */
        $('input#submitDecodeButton').click(loadLogs);

        /** Hide the result textarea */
        $('div#publicKeyToDisplay, div#privateKeyToDisplay').hide();

        /** Register the click of the button to encode the message. */
        $('input#publicKeyToReplace, input#privateKeyToReplace').keypress(function(e) {
            // if the user pressed ENTER and the value of the field is empty
            if (e.which == 13 && $(this).attr("value") == "") {
                alert("You need to provide the value for this field.");
                $(this).focus();
                return;

            } else if (e.which == 13 && $(this).attr("value").split("(").length != 3) {
                alert("You need to Copy and Paste the Key Strings as shown.");
                $(this).focus();
                return;
            }
            var idT = $(this).attr("id") == "publicKeyToReplace" ? "publicKeyToDisplay" : "privateKeyToDisplay";
            if (e.which == 13) {
                var selector = 'div[id='+ idT +']';
                $(selector).html($(this).attr("value"));
                $(selector).show();
                $(this).hide();
                var keys = $(this).attr("value").split("(")[2].replace(")","").replace(" ","").split(",");
                if (idT == "publicKeyToDisplay") {
                    $("#n").val(keys[0]);
                    $("#e").val(keys[1]);
                } else {
                    $("#n").val(keys[0]);
                    $("#d").val(keys[1]);
                }
            }
        });

        $("textarea#decodedMessage").focus(function() {
            $this = $(this);
            $this.select();

            // Work around Chrome's little problem
            $this.mouseup(function() {
                // Prevent further mouseup intervention
                $this.unbind("mouseup");
                return false;
            });
        });

      });

      /**
       * Load the logs from the decoding process.
       */
      function loadLogs() {
        if ($("#n").val() == "" || $("#e").val() == "") {
            alert("You need to provide the Public Keys keys! Copy and Paste them!");
            $("input#publicKeyToReplace").focus();
            return;

        } else if ($("#d").val() == "") {
            alert("You need to provide the Private Keys keys! Copy and Paste them!");
            $("input#privateKeyToReplace").focus();
            return;

        } else if ($("textarea#m").val() == null || $("textarea#m").val() == "") {
            alert("You need to provide a message to decode!");
            $("textarea#m").focus();
            var div = $("textarea#m").parents("div.control-group");
            div.addClass("error");
            return
        }
        var url = "${createLink(controller:'decoder', action:'decode')}";
        var params = {};
        params["n"] = $("#n").val();
        params["e"] = $("#e").val();
        params["d"] = $("#d").val();
        params["m"] = $("textarea#m").val();
        $.getJSON(url, params,
            function(decodeResult){
               if (decodeResult.error != undefined) {
                   alert("An error occurred while encoding: " + decodeResult.error);
                   return;
               }
               var myHTMLString = ''
               for(var i = 0 ; i < decodeResult.computationLog.length; i++) {
                   myHTMLString += makeHTMLLogEntry(decodeResult.computationLog[i], i);
               }
              $('pre#logCalculation').html(myHTMLString);
              $('textarea#decodedMessage').val(decodeResult.decodedMessage);
              $('div#decodedMessageArea').show();
              $("textarea#decodedMessage").focus();
            }
        );
        var div = $("textarea#m").parents("div.control-group");
        div.removeClass("error");
      }

      /**
       * Make the row for the log entries received from the server.
       */
      function makeHTMLLogEntry(logEntry, index) {
         return "<ol class=\"linenums\"><li class=\"L"+index+"\"><span class=\"kwd\">"+logEntry+"</span></li></ol>";
      }
    </script>

   </head>

<body>

  <g:if test="${flash.error}">
    <div class="alert alert-error">
        <button class="close" data-dismiss="alert">×</button>
        <h4 class="alert-heading">Error</h4>
        <g:message code="${flash.error}" />
    </div>
  </g:if>

  <div class="hero-unit">
    <h1>Decode RSA Messages</h1>
    <p>Decoding messages using the Private and Public keys.</p>
    <g:if test="${!params.n && !params.e && !params.d}">
        <p><g:link controller="key" action="random" class="btn btn-primary btn-large">
      Generate Random Keys &raquo;</g:link></p>
    </g:if>
  </div>

  <div class="row-fluid">

    <div class="span4">
      <h2>RSA Keys</h2>
      <p>Use any set of keys.</p>
      <g:if test="${params.n && params.e && params.d}">
         <div class="alert alert-info">
           <h4 class="alert-heading">Public Key</h4>
           <decodr:rsaKey n="${params.n}" e="${params.e}" />
         </div>
         <div class="alert alert-error">
           <h4 class="alert-heading">Private Key</h4>
           <decodr:rsaKey n="${params.n}" e="${params.d}" />
         </div>
      </g:if>
      <g:else>
         <div class="alert alert-info">
           <h4 class="alert-heading">Public Key</h4>
           <input id="publicKeyToReplace" type="text" value="">
           <div id="publicKeyToDisplay"></div>
         </div>
         <div class="alert alert-error">
           <h4 class="alert-heading">Private Key</h4>
           <input id="privateKeyToReplace" type="text" value="">
           <div id="privateKeyToDisplay"></div>
         </div>
      </g:else>
    </div><!--/span-->

    <div class="span4">
        <h2>Decode Message</h2>
        <g:form class="well">
          <div id="messageGroup" class="control-group">
            <label class="control-label" for="m">Decode Message</label>
            <div class="control">
                <textarea class="input-xlarge" name="m" id="m" rows="3" cols="50"></textarea>
                <input type="hidden" id="n" name="n" value="${params.n ? params.n : ''}">
                <input type="hidden" id="e" name="e" value="${params.e ? params.e : ''}">
                <input type="hidden" id="d" name="d" value="${params.d ? params.d : ''}">
            </div>
          </div>
          <input id="submitDecodeButton" class="btn btn-primary btn-medium" type="button"  value="Decode Message &raquo;">
        </g:form>
    </div><!--/span-->

    <div class="span4">
      <h2>Receiving a message</h2>
      <p>Split the message by the delimiter "-"
        <li>Retrieve the original value<BR>
        <li>Ascii(x) = x ^ D mod N
        <li>Convert/concact each Ascii(x).
      </p>
    </div><!--/span-->

  </div><!--/row-->
  
  <div class="row-fluid">
    <div id="decodedMessageArea" class="span4">
        <h2>Decoded message...</h2>
          <textarea id="decodedMessage" class="input-xlarge uneditable-textarea" rows="5" cols="50"></textarea>
          <BR/>
          <!-- input id="copyEncodedMessageButton" class="btn btn-primary btn-medium" type="button" value="Copy to clipboard &raquo;" -->
    </div>

    <div class="span8">
        <h2>Decoding a message</h2>
        <pre id="logCalculation" class="prettyprint linenums prettyprinted">
          The result of calculation will be displayed here...
        </pre>
    </div>

  </div>
</body>
</html>