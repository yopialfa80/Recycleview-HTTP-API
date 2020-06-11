
<?php 
	require "koneksi.php";

	$id = $_POST["id"];
	$name = $_POST["name"];
	$stats = $_POST["stats"];
	$namaPicture = $_POST["namaPicture"];
	$picture = $_POST["picture"];

	$path = "image/$namaPicture.png";
	$actualpath = "http://192.168.100.4:8080/GitHub/Http/$path";

	$locationfile = mysqli_real_escape_string($koneksi,$actualpath);

	mysqli_query($koneksi,"UPDATE user SET name='$name', stats='$stats', picture='$locationfile' WHERE id='$id'");
	
	file_put_contents($path,base64_decode($picture));
?>