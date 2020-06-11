
<?php 
	require "koneksi.php";

	$name = $_POST["name"];
	$stats = $_POST["stats"];
	$namaPicture = $_POST["namaPicture"];
	$picture = $_POST["picture"];

	$path = "image/$namaPicture.png";
	$actualpath = "http://192.168.100.4:8080/GitHub/Http/$path";

	$locationfile = mysqli_real_escape_string($koneksi,$actualpath);

	mysqli_query($koneksi,"insert into user (name, stats, picture) VALUES ('$name', '$stats', '$locationfile')");
	
	file_put_contents($path,base64_decode($picture));
?>