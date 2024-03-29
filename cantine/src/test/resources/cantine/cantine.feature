#language:fr
#
# Attentionmettre fichier en utf-8 pour que ça marche.
#
# voir https://isabelle-blasquez.developpez.com/tutoriels/java/cucumber-test-bdd/
#
Fonctionnalité: inscription éléve à la cantine.
  un enseignant veut inscrire un élève à la cantine

  Contexte: 
    Soit des enseignants existent:
      | id  | fisrtName | lastName |
      | abc | Paul      | DUPOND   |
      | def | Françoise | MULLER   |
    Soit des eleves existent:
      | id  | fisrtName | lastName |
      | abc | François  | THOMAS   |
      | abc | Marc      | DUPUIS   |

  Plan du scénario: inscription
    Soit je suis authentifié en tant que "<nom_enseignant>"
    Quand je "<nom_enseignant>" tente d inscrire l'élève "<nom_eleve>"
    Alors "<nom_eleve>" est inscrit

    Exemples: 
      | nom_enseignant | nom_eleve |
      | DUPOND         | THOMAS    |
      | DUPOND         | DUPUIS    |

  Plan du scénario: désinscription
    Soit je suis authentifié en tant que "<nom_enseignant>"
    Soit je "<nom_enseignant>" tente d inscrire l'élève "<nom_eleve>"
    Alors "<nom_eleve>" est inscrit
    Quand je "<nom_enseignant>" tente de désinscrire l'élève "<nom_eleve>"
    Alors l éléve "<nom_eleve>" n'est pas inscrit

    Exemples: 
      | nom_enseignant | nom_eleve |
      | DUPOND         | THOMAS    |
      | DUPOND         | DUPUIS    |

  Plan du scénario: je ne suis pas authentifié et Inscription
    Soit je "<nom_enseignant>" n'est pas authentifié
    Soit l éléve "<nom_eleve>" n'est pas inscrit
    Quand je "<nom_enseignant>" tente d inscrire l'élève "<nom_eleve>"
    Alors l éléve "<nom_eleve>" n'est pas inscrit
    Et une alerte pour identification de l enseignant impossible

    Exemples: 
      | nom_enseignant | nom_eleve |
      | DUPOND         | THOMAS    |
      | DUPOND         | DUPUIS    |

  Plan du scénario: je ne suis pas authentifié et desinscription
    Soit je suis authentifié en tant que "<nom_enseignant>"
    Soit je "<nom_enseignant>" tente d inscrire l'élève "<nom_eleve>"
    Alors "<nom_eleve>" est inscrit
    Soit je "<nom_enseignant2>" n'est pas authentifié
    Quand je "<nom_enseignant2>" tente de désinscrire l'élève "<nom_eleve>"
    Alors "<nom_eleve>" est inscrit
    Et une alerte pour identification de l enseignant impossible

    Exemples: 
      | nom_enseignant | nom_enseignant2 | nom_eleve |
      | DUPOND         | MULLER          | THOMAS    |
      | DUPOND         | MULLER          | DUPUIS    |
