<?php 
	require "koneksi.php";

	$stmt = $koneksi->prepare("SELECT id, name, stats, picture FROM user");
	
	$stmt->execute();
	
	$stmt->bind_result($id, $name, $stats, $picture);
	
	$products = array(); 
	
	while($stmt->fetch()){
		$temp = array();
		$temp['id'] = $id;
		$temp['name'] = $name; 
		$temp['stats'] = $stats; 
		$temp['picture'] = $picture; 
	
		array_push($products, $temp);
	}
	
	echo json_encode($products);