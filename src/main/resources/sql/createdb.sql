root/yimingbuyi

CREATE DATABASE yiming DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE USER 'yiming'@'%' IDENTIFIED BY 'Aa123456!@@';

GRANT SELECT, DELETE, UPDATE, CREATE, DROP ON yiming.* TO yiming@'%'
IDENTIFIED BY 'Aa123456!@@';

flush privileges;



