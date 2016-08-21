-- Create syntax for 'emails'

CREATE TABLE `emails` (
  `id` bigint(100) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT 'No Name',
  `email` varchar(100) DEFAULT 'No Email Address',
  `status` varchar(1) DEFAULT 'A',
  `digitalSign` varchar(1) DEFAULT 'N',
  `iPhoneCustomRingTone` varchar(1) DEFAULT 'N',
  `shareDocWhatsApp` varchar(1) DEFAULT 'N',
  `decompileJar` varchar(1) DEFAULT 'N',
  `debugJavaPrgm` varchar(1) DEFAULT 'N',
  `solveMathIphone` varchar(1) DEFAULT 'N',
  `orgPhotosMac` varchar(1) DEFAULT 'N',
  `trackFlight` varchar(1) DEFAULT 'N',
  `photosBackup` varchar(1) DEFAULT 'N',
  `virtualBox` varchar(1) DEFAULT 'N',
  `jpg-pdf` varchar(1) DEFAULT 'N',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=108282 DEFAULT CHARSET=latin1;
