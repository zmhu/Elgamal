## BTW
- 以前帮别人做毕业论文的，偶然看到代码，发现对消息m的支持不够好（只能支持数字和字母），现在改成能用，不过消息m的长度也增加了许多

## 算法
- 密钥对产生办法：首先选择一个素数p和两个随机数g 、x （g、 x < p ），计算 y ≡ g^x（ mod p ） ，已知y，求解x是非常困难的事情（离散对数求解难题），则其公钥为 y, g 和p ，私钥是x ，g和p可由一组用户共享。
- ElGamal用于数字签名。被签信息为M，首先选择一个随机数k ， k与 p - 1互素，计算：
```
a ≡ g^k ( mod p )
```
- 再用扩展 Euclidean 算法对下面方程求解b：
```
M ≡ xa + kb ( mod p - 1 )
```
- 签名就是( a, b )。随机数k须丢弃。
- 验证时要验证下式：
```
y^a * a^b ( mod p ) ≡ g^M ( mod p )
```
- 同时一定要检验是否满足1<= a < p。否则签名容易伪造。
- ElGamal用于加密。被加密信息为M，首先选择一个随机数k，k与 p - 1互质，计算
```
a ≡ g^k ( mod p )
b ≡ y^k M ( mod p )
```
- ( a, b )为密文，是明文的两倍长。解密时计算
```
M ≡ b / a^x ( mod p )
```
- ElGamal签名的安全性依赖于乘法群(IFp)* 上的离散对数计算。素数p必须足够大，且p-1至少包含一个大素数
