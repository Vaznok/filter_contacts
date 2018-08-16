Vagrant.configure("2") do |config|
	config.vm.box = "ubuntu/xenial64"
  
	config.vm.network "forwarded_port", guest: 90, host: 90
	
	config.vm.provision "shell", path: "vag-setup.sh"
	
	#sudo apt-get install -y curl vim zip unzip git-all postgresql-9.6 postgresql-contrib-9.6 postgresql-client-9.6 xfce4 chromium-browser code
end
