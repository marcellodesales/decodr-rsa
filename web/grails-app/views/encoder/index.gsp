<html>
   <head>
      <meta name="layout" content="main"/>
      <r:require modules="bootstrap"/>
   </head>
<body>

  <div class="hero-unit">
    <h1>Encoding RSA Messages</h1>
    <p>Encode messages using a set of public keys.</p>

    <g:if test="${params.n && params.e}">
      <g:link class="btn btn-primary btn-large"
          controller="encoder" action="encode"
          params="${[n:params.n, e:params.e]}">
          Encode Message &raquo;</g:link>
   </g:if>

  </div>

  <div class="row-fluid">
    <div class="span4">
      <h2>Sending a message</h2>
      <p>Apply a conversion function for each char.
        <li>Convert a String into chars<BR>
        <li>Randomly select a block of number x
        <li>Block(x) = x ^ E mod N
        <li>Concat all the blocks with a delimiter "-"
      </p>
      <p><a class="btn" href="#">View details &raquo;</a></p>
    </div><!--/span-->
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
        <label>Encode Message</label>
        <textarea class="input-xlarge" name="m" id="m" rows="3">
        </textarea>
        <input type="hidden" name="n" value="${params.n}">
        <input type="hidden" name="e" value="${params.e}">
        <g:actionSubmit action="encode" class="btn btn-primary save" value="Encode Message" />
       </g:form>
    </g:else>

    </div><!--/span-->
  </div><!--/row-->
</body>
</html>