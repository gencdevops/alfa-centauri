<h1>CRAFTGATE YEMEK SİPARİŞ SİSTEMİ</h1>
<br>
<h2>Servisler</h2>

<li><strong>cg-common</strong> :         Ortak kullanılan sınıfları içerir</li>
<li><strong>cg-config-server</strong> :  Config server</li>
<li><strong>cg-restaurant</strong> :     Restaurant servisi</li>
<li><strong>cg-order</strong> :          Order servisi</li>
<li><strong>cg-registry</strong> :       Euroka server</li>
<br>
<br>

<h3>cg-common</h3>
Bu mikro servis diğer servislerin kullanması için yardımcı görevi görür. İçerisinde dto, model ve request paketlerini içerir.
<br>
<br>
<h3>cg-registry</h3>
Bu mikro servis euroka serverdır. Diğer servisler bu servise kayıt olarak
birbirleri arasında olan iletişimi kolay bir şekilde gerçekleştirebilir.
<br>
<br>
<h3>cg-restaurant</h3>
Siparişleri order servisine yönlendirir. İçerisinde
Order, Branch, Product ve Supplier ile ilgili işlemleri barındırır.
<br>
<br>
<h3>cg-order</h3>
Orderın üzerinde placeOrder methodu bulunmaktadır. placeOrder methodu payment'tan önce postgre database'e order ve orderoutbox diye iki kayıt atmaktadır.
Order'ın statusu paymenttan önce RECEIVED'a çekilmektedir. Ödeme ve order  adımları transactional bir method içerisinde gerçekleşmektedir.
Eğer ödeme alınır ve orderın statusu preparinge çekilemezse, kafka içerisinde preparinge çekilir. Order service içerisinde schedular job çalışmaktadır.
Atıl kalan orderoutbox kayıtlarını temizler. Eğer kafka da çökerse slack üzerinden alert yazısı gönderilir. 
<br>
<br>
<h3>cg-config-server</h3>
Diğer servislerin ayarlarını barındıran configuration serverdır. Mikroservislerin ayarları bu servis içinde barındırılır.
<br>
<br>

<h2>Genel Akış</h2>
Restoran mikroservisinde branch, product, supplier, branch_product_price adında 4 adet tablo bulunmaktadır.
Uygulamada ilk öncelikle supplier oluşturulmalıdır. Daha sonra esupplier_id ile branch oluşturulur. Daha sonra
supplier_id ile product oluşturulukrne default fiyatı verilir. Eğer branche özel fiyat yoksa branch_product_price tablosuna ekstra update atılmaz.
Eğer branch bazlı fiyat varsa product ve branch id ile product_price tablosuna update atılır. İlk öncelikle order ve restaurant mikroservis 
branch_product_price tablosunda update edilen branche özel fiyatına bakar. Bu yoksa default fiyatı baz alır. Daha sonra ilgili bilgilerle placeOrder
mikroservisi çağrılır.
<br>
<br>
<br>
<br>

## Tech Stack
    Spring boot
    Spring cloud
    Feign client
    Redis cache
    Kafka
    Object mapper
    Mapstruct
    Euroka
    Spring config server
    Docker
    Slack API
    Lombok
    OpenAPI

<br>
<br>
<br>

## Projenin ayağa kaldırılması
    docker compose up
    cg-registry
    cg-config-server
    cg-restaurant
    cg-order

<img width="647" alt="resim" src="https://user-images.githubusercontent.com/52275789/223154162-9d039051-d90c-4fe2-a31b-bb473be92a7c.png">

<img width="647" alt="resim" src="https://user-images.githubusercontent.com/52275789/223154829-54a50ed4-46d2-45af-ae7b-b486423d8779.png">

<img width="1669" alt="image" src="https://user-images.githubusercontent.com/98235097/223166324-b8c92730-a038-4c1b-a02e-6263060a46bc.png">

<img width="1666" alt="image" src="https://user-images.githubusercontent.com/98235097/223166481-9520eaa2-f4a3-4469-9e16-3482b0eb3502.png">

<img width="1666" alt="image" src="https://user-images.githubusercontent.com/98235097/223166559-94d38fa6-bb57-4ead-9479-2583ed44151b.png">

<img width="1666" alt="image" src="https://user-images.githubusercontent.com/98235097/223166642-4fd4156f-c9d9-4049-abe6-3ba37444b8e8.png">


