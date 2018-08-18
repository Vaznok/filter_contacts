[![Build Status](https://travis-ci.com/Vaznok/filter_contacts.svg?branch=master)](https://travis-ci.com/Vaznok/filter_contacts)

### Postgresql 9.6
	Launch docker and CLI
	- cd ${project.folder}
	- docker image build -t vaznok/postgres .
  	- docker container run --name postgresdb -d -p 5555:5432 -e POSTGRES_PASSWORD=root vaznok/postgres
  	- docker container start postgresdb
	
### Building
	mvn clean spring-boot:run -pl contacts-web

### Request example
	http://localhost:8080/hello/contacts?nameFilter=%5EA.%2A%24

### Respone output
	Simple json file with filtered data. 

### Notes
	 1. In order to use different regexp, it must be encoded to special URL format. It can be done here https://www.urlencoder.org/
	 2. There are 1 000 000 rows in database. 
	 3. If a json's reader can't open gotten json file, open it with standart Notepad. It issue appears when json file is to big.
	 4. To manage RAM overflow and increase speed of request/response were used:
	 	- configurable (config ehcache.xml) cache, implementation `ehcache2.0`. Where we can allocate RAM memory according to the server power.
		- configurable query select volume in (config application.yml) to filter data by pieces.
	 5. ContactCache class was created separately from ContactServiceImpl class because of the next reason:
	    Spring cache is not working when calling cached method from another method of the same bean.
	    https://stackoverflow.com/questions/16899604/spring-cache-cacheable-not-working-while-calling-from-another-method-of-the-s
	 6. In ContactServiceImpl class method findByRegexp(List<Contact> contacts, String regexp) I didn't use parallel stream filtering,
	    because on my machine for more than 30 simultaneous requests it works slower then simple stream due to higher CPU utilization.
	    For the little number of simultaneously request one thread stream works slower but fast enough for client.