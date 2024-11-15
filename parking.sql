-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Mer 21 Novembre 2018 à 18:01
-- Version du serveur :  10.1.21-MariaDB
-- Version de PHP :  5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `parking`
--

-- --------------------------------------------------------

--
-- Structure de la table `parking`
--

CREATE TABLE `parking` (
  `id` int(11) NOT NULL,
  `idregion` int(11) NOT NULL,
  `prix` int(11) NOT NULL,
  `nbrplaces` int(11) NOT NULL,
  `nbrplacesdispo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `parking`
--

INSERT INTO `parking` (`id`, `idregion`, `prix`, `nbrplaces`, `nbrplacesdispo`) VALUES
(1, 1, 3, 20, 20),
(2, 1, 2, 15, 15),
(3, 1, 5, 10, 10),
(4, 1, 3, 9, 9),
(5, 1, 4, 11, 11),
(6, 2, 2, 13, 13),
(7, 2, 3, 20, 20),
(8, 2, 2, 18, 18),
(9, 2, 2, 16, 16),
(10, 2, 2, 12, 12),
(11, 2, 3, 20, 20),
(12, 2, 4, 9, 9),
(13, 2, 4, 10, 10),
(14, 3, 7, 23, 23),
(15, 3, 8, 25, 25),
(16, 3, 8, 23, 23),
(17, 3, 10, 46, 46),
(26, 3, 9, 24, 24),
(27, 5, 3, 24, 24),
(28, 5, 2, 13, 13),
(29, 5, 4, 21, 21),
(30, 6, 3, 9, 9),
(31, 6, 3, 11, 11),
(32, 7, 5, 22, 22),
(33, 7, 5, 23, 23),
(34, 7, 4, 21, 21),
(35, 7, 6, 17, 17),
(36, 7, 3, 11, 11),
(37, 8, 6, 26, 26),
(38, 8, 5, 23, 23),
(39, 8, 6, 21, 21),
(40, 9, 4, 18, 18),
(41, 9, 5, 15, 15),
(42, 9, 3, 13, 13),
(43, 9, 6, 19, 19),
(44, 9, 3, 16, 16),
(45, 9, 4, 15, 15),
(46, 10, 6, 18, 18),
(47, 10, 6, 15, 15),
(48, 10, 3, 13, 13),
(49, 10, 3, 13, 13),
(50, 10, 3, 22, 22),
(51, 10, 4, 10, 10),
(52, 10, 6, 17, 17),
(53, 10, 4, 13, 13),
(54, 11, 7, 19, 19),
(55, 11, 7, 12, 12),
(56, 11, 2, 19, 19),
(57, 11, 6, 17, 17),
(58, 11, 5, 18, 18),
(59, 12, 2, 8, 8),
(60, 12, 4, 12, 12),
(61, 12, 2, 10, 10),
(62, 4, 4, 23, 23),
(63, 4, 7, 10, 10),
(64, 4, 4, 10, 10),
(65, 4, 7, 15, 15),
(66, 4, 5, 13, 13),
(67, 4, 6, 11, 11),
(68, 4, 3, 20, 20),
(69, 4, 4, 13, 13),
(70, 4, 6, 19, 19);

-- --------------------------------------------------------

--
-- Structure de la table `region`
--

CREATE TABLE `region` (
  `id` int(11) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `nbrparking` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `region`
--

INSERT INTO `region` (`id`, `nom`, `nbrparking`) VALUES
(1, 'ainchok', 5),
(2, 'ainsbaa', 8),
(3, 'aindiab', 4),
(4, 'anfa', 9),
(5, 'sidi othmane', 3),
(6, 'moulay rchid', 2),
(7, 'hay hassani', 5),
(8, 'sidi maarouf', 3),
(9, 'centre ville', 6),
(10, 'mers sultan', 8),
(11, 'hay mohemmadi', 5),
(12, 'bournazel', 3);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `parking`
--
ALTER TABLE `parking`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `idregion` (`idregion`);

--
-- Index pour la table `region`
--
ALTER TABLE `region`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `parking`
--
ALTER TABLE `parking`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;
--
-- AUTO_INCREMENT pour la table `region`
--
ALTER TABLE `region`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `parking`
--
ALTER TABLE `parking`
  ADD CONSTRAINT `parking_ibfk_1` FOREIGN KEY (`idregion`) REFERENCES `region` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
