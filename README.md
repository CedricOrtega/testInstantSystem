# testInstantSystem

Pour développer l'API permettant de communiquer avec l'API soumis par la ville de Poitiers https://data.grandpoitiers.fr/api/records/1.0 .
J'ai décidé d'utiliser le client REST déclaratif OpenFeign.
Il me permet d'appeler l'API nécessaire pour le test, mais aussi de rendre générique mon appel GET, qui me permettra en écrivant une seule méthode d'appeler une API d'une autre ville.

Je mappe par la suite la réponse de l'API avec un DTO structuré qui me permettra de traiter cette réponse et délivrer une réponse HTTP GET précise :
![image](https://user-images.githubusercontent.com/32506449/200187520-8aa1131a-1e86-4815-9ec4-a6e90da97d25.png)

En ce qui concerne la suite.
J'ai décidé à l'aide d'une méthode présente dans une classe technique. De trié par ordre décroissant la distance des parkings selon la position actuelle de l'utilisateur.
En parlant de la position en temps réel de l'utilisateur.
Je suis parti du principe que l'on envoie en requête à notre API la position WGS84 actuelle de l'utilisateur.
Mais aussi le nom de la ville dans laquelle il est actuellement.
Pourquoi ?
Car cela permet de savoir selon la ville qu'elle est l'API en Open Data que nous allons utiliser.
Nous recevons donc en requête la ville par exemple : Poitiers (notre API est non sensible à la casse) et nous déterminons dans le code qu'il faut appeler l'API https://data.grandpoitiers.fr/api/ .

Dans ma solution que je propose, j'ai décidé de ne pas persister les URLs des Open data des différentes villes pour une meilleure performance et rendre la solution du test plus légère et ne pas dépendre d'une bdd.
Les URLs sont dans des constantes et les noms de villes dans une Enumération.
Mais bien-sûre après en ayant des centaines de villes, il serait intéressant de persister les villes possédant des URLs d'API qu'on appellerait.
On pourrait pour faciliter le suivi des modifications de bases de données utilisé la solution liquibase en cas d'URL changeant ou d'ajout d'une ville qui posséderait une toute nouvelle API.

Tests Unitaire:

Pour ce qui en est des tests unitaires.
On pourrait rajouter la librairie Jacoco pour obtenir le code coverage se basant sur les critères par défaut de Sonar. Et non utilisé le coverage de notre IDE (pour ma part IntelliJ).

J'ai rajouté ma collection Postman mais étant donné l'export voici une capture de mon test postman.

![image](https://user-images.githubusercontent.com/32506449/200188229-9b7c7c26-efde-47ef-ba1d-0bda12696ff1.png)

Exception :

Vous pouvez essayer de chercher une ville qui n'est pas gérée par l'API par exemple : Listembourg. Une exception customisé se lancera (404 - NOT FOUND).
Les exceptions sont mises en place dans la solution pour guider au maximum le consommateur de l'API et l'aider.
![image](https://user-images.githubusercontent.com/32506449/200188934-d1a63a62-57a6-4ff1-b3e0-36a189d20024.png)

Documentation :

Swagger est utilisé pour permettre de documenter l'API mise en place et pouvoir la tester elle est disponible à cet URL : http://{host}:8080/swagger-ui/index.html.
Pour permettre une génération d'une JavaDoc, des commentaires ont été faits au niveau des classes et des méthodes.

Points d'amélioration importante:

- Je me suis basé sur le screen envoyé pour apporter une solution technique.
  Plus haut, je parlais de trier les parkings disponibles a proximité selon la distance de l'utilisateur.
  Celà est géré mais il serait très interessant de mettre en place une pagination dans la réponse de l'API permettant à l'équipe front de pourquoi par trié par places   disponible, taux de remplissage ou autre. Par faute de temps et étant un nice-to-have je ne m'en suis pas occupé.

- Ma solution apportée pour gérer les différentes villes et pour moi bonne. Mais je suis sûre qu'il y a encore une solution plus performante et optimisable.
  Par exemple, une personne à cheval entre 2 villes serait intéressée d'avoir les parkings à proximité de ces 2 villes. Donc pourquoi pas faire des liens entre des villes   et faire un découpage des villes avec le WGS84. Mais cela demanderait beaucoup d'effort. Peut-être qu'une solution existe déjà, je n'ai pas encore investigué.
  
 - Je ne suis pas content lors de mon traitement des 2 réponses des APIs de l'Open data. Je souhaitais utiliser MapStruct pour générer le mappage des réponses d'API dans le DTO que j'envoie en réponse. Mais je ne suis pas non plus content de ma double boucle for même si elle fait très bien le travaille. On a du coup une méthode trop verbeuse. Je refactoriserai en utilisant les lambdas et/ou mapping customisé en utilisant l'annotation @Qualifier de Map struct.
Mais comme dis plus haut, le code en état fait le travaille et je n'ai pas voulu perdre trop de temps et me consacrer à d'autres partie comme les tests qui prennent du temps, la documentation, exception.
