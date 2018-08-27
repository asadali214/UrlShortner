use urlShortner;

drop table urlShortner.click;
drop table urlShortner.url;

CREATE TABLE `Url` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ExpiryDays` int(11),
  `DateCreated` date DEFAULT NULL,
  `ShortUrl` varchar(50) DEFAULT NULL,
  `LongUrl` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;



CREATE TABLE `Click` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `DateClicked` date DEFAULT NULL,
  `BrowserClicked` varchar(50) DEFAULT NULL,
  `PlatformClicked` varchar(50) DEFAULT NULL,
  `UrlId` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  FOREIGN KEY (`UrlId`)
        REFERENCES Url(Id)
        ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


SELECT * FROM urlShortner.url;
SELECT * FROM urlShortner.click;