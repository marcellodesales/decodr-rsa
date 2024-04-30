# CriptOnline - Encrypted messages with RSA

Here will demonstrate with an example how the real algorithm
works... It's demonstrated here the 3 steps used: 

* Generating the keys,
* sending a message,
* receiving a message.

We'll encode my name `Marcello Alves de Sales Junior`

# Generating the keys

```console
-> Configuring random prime numbers
        P = 5563
        Q = 769

-> Calculating public keys
          N = P * Q; N = 4277947
          
-> FI = (P-1) * (Q-1); FI = 4271616

-> Calculating (E)
           While MCD(n >= 2 , 4271616) != 1
           MCD(2 , 4271616) = 2
           MCD(3 , 4271616) = 3
           MCD(4 , 4271616) = 4
           MCD(5 , 4271616) = 1 Correct!
          E = 5

       Public Key (N,E) = (4277947 , 5)

-> Calculating private keys
         Initializing (p1,p2,p3) = (1, 0 , FI(n))
         Initializing (q1,q2,q3) = (0, 1 ,  E  ))
         While q3 != 0
             quoc = p3 / q3
             (t1,t2,t3) = (p1,p2,p3) - quoc * (q1,q2,q3)
             After, arrange the values:
             (p1,p2,p3) = (q1,q2,q3)
             (q1,q2,q3) = (t1,t2,t3)

           (5 <> 0) , then:
             quoc = 4271616 / 5 = 854323
             (t1,t2,t3) = (0,1,5) - 854323 * (1,-854323,1) = (1,-854323,1)
             (p1,p2,p3) = (1,-854323,1)
             (q1,q2,q3) = (1,-854323,1)

           (1 <> 0) , then:
             quoc = 5 / 1 = 5
             (t1,t2,t3) = (1,-854323,1) - 5 * (-5,4271616,0) = (-5,4271616,0)
             (p1,p2,p3) = (-5,4271616,0)
             (q1,q2,q3) = (-5,4271616,0)

         q3 is zero(0). Now, verify the value of p2. In case of negative, invert it by summing it with FI. 
         (represent the negative number of z(n) by a positive.)

         u2 = -854323;
          Since u2 is negative, we have:
          D = u2 + FI; D = -854323 + 4271616 = 3417293

      Private Key (N,D) = (4277947, 3417293);
```

## Key Information

```console
        Prime P: 5563
        Prime Q: 769

        Public Key (N, E) = (4277947, 5)
        Private Key (N, D) = (4277947, 3417293)
```

# Sending message

* Simulation of sending an encrypted message with the keys above

```console
-> Input Message
Marcello Alves de Sales Junior

-> Setting the receiver's public key
(N , E) = (4277947 , 5)

-> Transforming the message to ASCII code
177197214199201208208211132165208218201215132200201132183197208201215132174217210205211214

-> Configuring randomly selected blocks from the ASCII message
Bloco(x) = x ^ E mod N

Bloco(177) = 177 ^ 5 mod 4277947 = 3454934
Bloco(19721) = 19721 ^ 5 mod 4277947 = 2133124
Bloco(4199) = 4199 ^ 5 mod 4277947 = 465541
Bloco(20) = 20 ^ 5 mod 4277947 = 3200000
Bloco(120) = 120 ^ 5 mod 4277947 = 2660248
Bloco(820) = 820 ^ 5 mod 4277947 = 2556484
Bloco(82111) = 82111 ^ 5 mod 4277947 = 1010288
Bloco(321) = 321 ^ 5 mod 4277947 = 3110171
Bloco(6520) = 6520 ^ 5 mod 4277947 = 1025599
Bloco(8) = 8 ^ 5 mod 4277947 = 32768
Bloco(21) = 21 ^ 5 mod 4277947 = 4084101
Bloco(8) = 8 ^ 5 mod 4277947 = 32768
Bloco(20) = 20 ^ 5 mod 4277947 = 3200000
Bloco(1215) = 1215 ^ 5 mod 4277947 = 2835925
Bloco(132) = 132 ^ 5 mod 4277947 = 3112883
Bloco(20020) = 20020 ^ 5 mod 4277947 = 2048173
Bloco(113) = 113 ^ 5 mod 4277947 = 3512011
Block(218) = 218 ^ 5 mod 4277947 = 2189444
Block(319720) = 319720 ^ 5 mod 4277947 = 96349
Block(8) = 8 ^ 5 mod 4277947 = 32768
Block(20) = 20 ^ 5 mod 4277947 = 3200000
Block(12) = 12 ^ 5 mod 4277947 = 248832
Block(151) = 151 ^ 5 mod 4277947 = 2398301
Block(32) = 32 ^ 5 mod 4277947 = 3608803
Block(17) = 17 ^ 5 mod 4277947 = 1419857
Block(42172) = 42172 ^ 5 mod 4277947 = 3502116
Block(10) = 10 ^ 5 mod 4277947 = 100000
Block(2052) = 2052 ^ 5 mod 4277947 = 4147294
Block(11214) = 11214 ^ 5 mod 4277947 = 2916482

-> Encrypted Message
3454934-2133124-465541-3200000-2660248-2556484-1010288-3110171-1025599-32768-4084101-32768-3200000-2835925
-3112883-2048173-3512011-2189444-96349-32768-3200000-248832-2398301-3608803-1419857-3502116-100000-4147294-2916482
```

# Receiving a message

```console
-> Encrypted Message
3454934-2133124-465541-3200000-2660248-2556484-1010288-3110171-1025599-32768-4084101-32768-3200000-2835925
-3112883-2048173-3512011-2189444-96349-32768-3200000-248832-2398301-3608803-1419857-3502116-100000-4147294-2916482

-> Setting the private key
(N , D) = (4277947 , 3417293)

-> Decrypting each block
Ascii(x) = x ^ D mod N

Ascii(3454934) = 3454934 ^ 3417293 mod 4277947 = 177
Ascii(2133124) = 2133124 ^ 3417293 mod 4277947 = 19721
Ascii(465541) = 465541 ^ 3417293 mod 4277947 = 4199
Ascii(3200000) = 3200000 ^ 3417293 mod 4277947 = 20
Ascii(2660248) = 2660248 ^ 3417293 mod 4277947 = 120
Ascii(2556484) = 2556484 ^ 3417293 mod 4277947 = 820
Ascii(1010288) = 1010288 ^ 3417293 mod 4277947 = 82111
Ascii(3110171) = 3110171 ^ 3417293 mod 4277947 = 321
Ascii(1025599) = 1025599 ^ 3417293 mod 4277947 = 6520
Ascii(32768) = 32768 ^ 3417293 mod 4277947 = 8
Ascii(4084101) = 4084101 ^ 3417293 mod 4277947 = 21
Ascii(32768) = 32768 ^ 3417293 mod 4277947 = 8
Ascii(3200000) = 3200000 ^ 3417293 mod 4277947 = 20
Ascii(2835925) = 2835925 ^ 3417293 mod 4277947 = 1215
Ascii(3112883) = 3112883 ^ 3417293 mod 4277947 = 132
Ascii(2048173) = 2048173 ^ 3417293 mod 4277947 = 20020
Ascii(3512011) = 3512011 ^ 3417293 mod 4277947 = 113
Ascii(2189444) = 2189444 ^ 3417293 mod 4277947 = 218
Ascii(96349) = 96349 ^ 3417293 mod 4277947 = 319720
Ascii(32768) = 32768 ^ 3417293 mod 4277947 = 8
Ascii(3200000) = 3200000 ^ 3417293 mod 4277947 = 20
Ascii(248832) = 248832 ^ 3417293 mod 4277947 = 12
Ascii(2398301) = 2398301 ^ 3417293 mod 4277947 = 151
Ascii(3608803) = 3608803 ^ 3417293 mod 4277947 = 32
Ascii(1419857) = 1419857 ^ 3417293 mod 4277947 = 17
Ascii(3502116) = 3502116 ^ 3417293 mod 4277947 = 42172
Ascii(100000) = 100000 ^ 3417293 mod 4277947 = 10
Ascii(4147294) = 4147294 ^ 3417293 mod 4277947 = 2052
Ascii(2916482) = 2916482 ^ 3417293 mod 4277947 = 11214

-> Complete message in ASCII
177197214199201208208211132165208218201215132200201132183197208201215132174217210205211214

-> Original Message
Marcello Alves de Sales Junior
```

> ® 2000 - 2008.
> Marcello Alves de Sales Junior
> Computer Science - Computing Institute - TCI
> Universidade Federal de Alagoas - UFAL - Brazil
