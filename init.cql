create keyspace ford with replication = {       
 'class' : 'SimpleStrategy', 'replication_factor' :1
  } ;

CREATE TABLE ford.machine (
machine_id int, type int, is_finish boolean, process_status int,
  timestamp bigint, primary key (machine_id,timestamp) 
);

CREATE TABLE ford.parsed_machine_data (
machine_id text, type text, is_finish text, process_status text,
  timestamp text, primary key (machine_id,timestamp) 
);

CREATE TABLE ford.machine_data (value text, primary key (value) );
