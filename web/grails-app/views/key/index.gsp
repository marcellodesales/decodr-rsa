<html>
   <head>
      <meta name="layout" content="main"/>
      <r:require modules="bootstrap"/>
      <title>RSA Public and Private Keys</title>
   </head>
<body>

  <div class="hero-unit">
    <h1>RSA Keys</h1>
    <p>This is where you can play with RSA Public and Private keys.</p>
    <p><g:link controller="key" action="random" class="btn btn-primary btn-large">
        Generate Random Keys &raquo;</g:link></p>
  </div>
  
  <div class="row-fluid">
    <div class="span4">
      <h2>Public Key</h2>
      <p>Composed by 2 prime numbers, such that:
        <li>P, Q: are large prime numbers<BR>
        <li>N = P * Q
        <li>ϕ(N) = (P-1) * (Q-1);
        <li>E = mcd(index >= 2, ϕ(N)) != 1
        <li><b>(N,E)</b>
      </p>
      <p><a class="btn" href="#">View details &raquo;</a></p>
    </div><!--/span-->
    <div class="span4">
      <h2>Private Key</h2>
      <p>Calculated from Extended Eucledean MDC:</p>
        <li>(p1,p2,p3) = (1, 0 , ϕ(N))
        <li>(q1,q2,q3) = (0, 1 , E ))</li>
        <li>While q3 != 0
          <BR>&nbsp;&nbsp;&nbsp;(t1,t2,t3) = (p1,p2,p3) - (p3 / q3) * (q1,q2,q3)</li>
        <li>(p1,p2,p3) = (q1,q2,q3)
        <li>(q1,q2,q3) = (t1,t2,t3)
        <li>D = (p2 &lt; 0) ? p2 + ϕ(N) : p2
        <li><b>(N,D)</b>

      <p><a class="btn" href="#">View details &raquo;</a></p>
    </div><!--/span-->
    <div class="span4">
      <h2>Sharing</h2>
      <p>Give the public keys to your friends, but keep your private keys as a secret.</p>
      <p><a class="btn" href="#">View details &raquo;</a></p>
    </div><!--/span-->
  </div><!--/row-->
</body>
</html>