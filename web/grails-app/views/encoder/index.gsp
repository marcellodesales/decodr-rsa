<html>
   <head>
      <meta name="layout" content="main"/>

      <r:require modules="bootstrap, jquery, prettify"/>

     <script type="text/javascript">
      $(document).ready(function(){
        $('#submitComputation').click(loadLogs);
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
        params["n"] = ${params.n};
        params["e"] = ${params.e};
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
              $('pre#logCalculation').html(myHTMLString)
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
    <h1>Encoding RSA Messages</h1>
    <p>Encode messages using a set of public keys.</p>

    <g:if test="${!params.n && !params.e}">
        <p><g:link controller="key" action="random" class="btn btn-primary btn-large">
      Generate Random &raquo;</g:link></p>
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
    </div><!--/span-->

    <div class="span4">
     <g:if test="${!params.n && !params.e}">
      <h2>Encode Message</h2>
     </g:if>
     <g:else>
        <g:form class="well">
          <div id="messageGroup" class="control-group">
            <label class="control-label" for="m">Encode Message</label>
            <div class="control">
                <textarea class="input-xlarge" name="m" id="m" rows="3" cols="50"></textarea>
                <input type="hidden" name="n" value="${params.n}">
                <input type="hidden" name="e" value="${params.e}">
            </div>
          </div>
          <input type="button" id="submitComputation" class="btn btn-primary btn-medium" value="Encode Message &raquo;">
        </g:form>
     </g:else>
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
    <div class="span8">
        <h2>Encoding a message</h2>
        <pre id="logCalculation" class="prettyprint linenums prettyprinted">
          The result of calculation will be displayed here...
        </pre>
    </div>
  </div>
</body>
</html>