<?php
	
	include_once('connection.php');

	$username=$_GET['username'];
	$nama=$_GET['nama'];
	$email=$_GET['email'];
	$password=$_GET['password'];
	$getdata=mysqli_query($koneksi, "SELECT*FROM user WHERE username='$username");
	$rows=mysqli_num_rows($getdata);
	$query = "UPDATE user SET username='$username', nama='$nama', email='$email', password='$password' ";

	$exeupdate=mysqli_query($koneksi,$query);

	$response=array();

	if ($rows>0) {
		$query = "UPDATE user SET username='$username', nama='$nama', email='$email', password='$password' ";
		$exeupdate=mysqli_query($koneksi,$query);

		if ($exeupdate) {
		$response['code']=1;
		$response['message']="berhasil update";
		
	} else{
		$response['code']=0;
		$response['message']="gagal update";
	}
} else{
		$response['code']=0;
		$response['message']="gagal update";
	}

	

	echo json_encode($response);



?>