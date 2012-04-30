<html>
   <head>
      <title>Encode Messages</title>
      <meta name="layout" content="main"/>

      <r:require modules="bootstrap, jquery, prettify, jqueryclipboard"/>

     <script type="text/javascript">
      $(document).ready(function(){

        /** Register the click of the button to encode the message. */
        $('input#submitComputationButton').click(loadLogs);

        $('input#submitDecodeButton').click(submitDecoderForm);

        /** Hide the result textarea */
        $('div#encodedMessageArea, div#publicKeyToDisplay').hide();

        /** Adds the clipboard function to the button */
//        $("#copyEncodedMessageButton").zclip({
//            path: "${resource(dir: 'js/jquery', file: 'ZeroClipboard.swf')}",
//            copy: function() {
 //               alert("Copied: " +  $(this).prev().val());
 //              return $(this).prev().val();
 //           }
 //       });

        $("textarea#encodedMessage").focus(function() {
            $this = $(this);
            $this.select();

            // Work around Chrome's little problem
            $this.mouseup(function() {
                // Prevent further mouseup intervention
                $this.unbind("mouseup");
                return false;
            });
        });

        /** Register the click of the button to encode the message. */
        $('input#publicKeyToReplace').keypress(function(e) {
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

      });

      /**
       * Load the logs from the encoding process.
       */
      function loadLogs() {
        if ($("textarea#m").val() == null || $("textarea#m").val() == "") {
            alert("You need to provide a message to encode!");
            $("textarea#m").focus();
            var div = $("textarea#m").parents("div.control-group");
            div.addClass("error");
            return
        }
        var url = "${createLink(controller:'encoder', action:'encode')}";
        var params = {};
        params["n"] = $("#n").val()
        params["e"] = $("#e").val()
        params["m"] = $("textarea#m").val();
        $.getJSON(url, params,
            function(encodeResult){
               if (encodeResult.error != undefined) {
                   alert("An error occurred while encoding: " + encodeResult.error);
                   return;
               }
               var myHTMLString = ''
               for(var i = 0 ; i < encodeResult.computationLog.length; i++) {
                   myHTMLString += makeHTMLLogEntry(encodeResult.computationLog[i], i);
               }
              $('pre#logCalculation').html(myHTMLString);
              $('textarea#encodedMessage').val(encodeResult.encodedMessage);
              $('div#encodedMessageArea').show();
              $("textarea#encodedMessage").focus();
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

      /** Submits the decoder form */
      function submitDecoderForm() {
         var encodedMessage = $('textarea#encodedMessage').val();
         $('form#decodeForm').find('input[name="m"]').attr("value", encodedMessage);
         $('form#decodeForm').submit();
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
    <h1>Encoding RSA Messages</h1>
    <p>Encode messages using a set of public keys.</p>

    <g:if test="${!params.n && !params.e}">
        <p><g:link controller="key" action="random" class="btn btn-primary btn-large">
      Generate Random Keys &raquo;</g:link></p>
    </g:if>

  </div>

  <div class="row-fluid">

    <div class="span4">
      <h2>Public Keys</h2>
      <p>Use any public key.</p>
      <g:if test="${params.n && params.e}">
         <div class="alert alert-info">
           <h4 class="alert-heading">Public Key</h4>
           <decodr:rsaKey n="${params.n}" e="${params.e}" />
         </div>
      </g:if>
      <g:else>
         <div class="alert alert-info">
           <h4 class="alert-heading">
              <a href="#" rel="popover" data-original-title="Public Key" 
                  data-content="Paste the entire key description like 'Publick Key (N,E) = (55471133, 7)'" >
                  Public Key</a></h4>
           <input id="publicKeyToReplace" type="text" value="">
           <div id="publicKeyToDisplay"></div>
         </div>
      </g:else>
    </div><!--/span-->

    <div class="span4">
      <h2>Encode Message</h2>
      <g:form class="well">
        <div id="messageGroup" class="control-group">
          <label class="control-label" for="m">Encode Message</label>
          <div class="control">
              <textarea class="input-xlarge" name="m" id="m" rows="3" cols="50"></textarea>
              <input id="n" name="n" type="hidden" value="${params.n ? params.n : ''}">
              <input id="e" name="e" type="hidden" value="${params.e ? params.e : ''}">
          </div>
        </div>
        <input id="submitComputationButton" class="btn btn-primary btn-medium" type="button"  value="Encode Message &raquo;">
      </g:form>
    </div><!--/span-->

    <div class="span4">
      <h2>Sending a message</h2>
      <p>Apply a conversion function for each char.
        <li>Convert a String into chars<BR>
        <li>Randomly select a block of number x
        <li>Block(x) = x ^ E mod N
        <li>Concat all the blocks with a delimiter "-"
      </p>
    </div><!--/span-->

  </div><!--/row-->
  
  <div class="row-fluid">
    <div id="encodedMessageArea" class="span4">
        <h2>Encoded message...</h2>
        <g:form name="decodeForm" id="decodeForm" class="well" controller="decoder" action="index">
          <textarea id="encodedMessage" class="input-xlarge uneditable-textarea" rows="5" cols="50"></textarea>
          <BR/>
          <!--a id="copyEncodedMessageButton" class="btn btn-primary btn-medium">
          Copy to clipboard &raquo;
          </a-->

          <div id="decodeMessageGroup" class="control-group">
            <label class="control-label" for="m">Start Decoding - Enter Private Key D</label>
            <div class="control">
                <input type="hidden" name="n" value="${params.n ?: ''}">
                <input type="hidden" name="e" value="${params.e ?: ''}">
                <input type="hidden" name="m" value="">
            </div>
          </div>
          <g:actionSubmit id="submitDecodeButton" class="btn btn-primary btn-medium" type="button"  value="Decode Encoded Message &raquo;" />
        </g:form>
    </div>

    <div class="span8">
        <h2>Encoding a message</h2>
        <pre id="logCalculation" class="prettyprint linenums prettyprinted">
          The result of calculation will be displayed here...
        </pre>
    </div>

  </div>
</body>
</html>