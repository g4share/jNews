CREATE TABLE IF NOT EXISTS `rss_location` (
  `name` char(20) NOT NULL,
  `url` char(50) NOT NULL,
  `enabled` bit(1) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=MEMORY DEFAULT CHARSET=latin1;


INSERT INTO `jNewsDB`.`rss_location` (
`name` ,
`url` ,
`enabled`
)
VALUES (
'Unimedia', 'http://unimedia.md/rss/news.xml', b '1'
);