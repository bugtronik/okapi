<%@ include file="menu.jsp" %>
<br><br>
<strong style="font-size: 25px;">Gestion des indicateurs</strong>
<br><br>
<c:if test="${ !empty erreur }">
	<p class="alert alert-danger col-lg-6 offset-lg-3" role="alert" style="text-align: center;">
		<strong><c:out value="${ erreur }" /></strong>
	</p>
</c:if>
<c:if test="${ !empty message }">
	<p class="alert alert-success col-lg-4 offset-lg-4" style="text-align: center;" role="alert">
		<c:out value="${ message }" />
	</p>
</c:if>

<c:if test="${ !empty message_erreur }">
	<p class="alert alert-danger col-lg-4 offset-lg-4" style="text-align: center;" role="alert">
		<c:out value="${ message_erreur }" />(Code: <c:out value="${ code_erreur }" />)
	</p>
</c:if>
<br>
<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addIndicateurModal" style="color:white;">
  Ajouter
</button>
<br>
<!-- Modal -->
<div class="modal fade" id="addIndicateurModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Création d'un indicateur</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" action="indicateur">
        <label for="libelle">Libellé</label>
        <input type="text" name="libelle" class="form-control" autocomplete="off" />
        <label for="code">Code : </label>
        <input type="text" name="code" autocomplete="off" class="form-control" />
		<label for="categorie">Categories : </label>
		<select name="id_categorie" id="categorie" class="form-control">
			<option value=""></option>
			<c:if test="${ !empty categories }">
				<c:forEach items="${ categories }" var="categorie">
					<option value="${ categorie.id }"><c:out value="${ categorie.libelle }" /></option>
				</c:forEach>
			</c:if>
		</select>
		<label>Sous-catégorie : </label>
		<select name="id_sous_categorie" id="souscategorie" class="form-control">
			<option value=""></option>
			<c:if test="${ !empty souscategories }">
				<c:forEach items="${ souscategories }" var="souscategorie">
					<option value="${ souscategorie.id }" class="${ souscategorie.id_categorie }"><c:out value="${ souscategorie.libelle }" /></option>
				</c:forEach>
			</c:if>
		</select>
		<label for="frequence">Fréquence : </label>
		<select name="id_frequence" id="frequence" class="form-control">
			<option value=""></option>
			<c:if test="${ !empty frequences }">
				<c:forEach items="${ frequences }" var="frequence">
					<option value="${ frequence.id }"><c:out value="${ frequence.libelle }" /></option>
				</c:forEach>
			</c:if>
		</select>
		<label for="unite">Unité : </label>
		<select name="id_unite" id="categorie" class="form-control">
			<option value=""></option>
			<c:if test="${ !empty unites }">
				<c:forEach items="${ unites }" var="unite">
					<option value="${ unite.id }"><c:out value="${ unite.libelle }" /></option>
				</c:forEach>
			</c:if>
		</select>
		<label>Calculé ?</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="calcul" value="oui" /> Oui &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="calcul" value="non" /> Non<br>
		<label>Service : </label>
		<select name="id_service" id="service" class="form-control">
			<option value=""></option>
			<c:if test="${ !empty services }">
				<c:forEach items="${ services }" var="service">
					<option value="${ service.id }"><c:out value="${ service.libelle }" /></option>
				</c:forEach>
			</c:if>
		</select>
		<label>Responsable : </label>
		<select name="id_utilisateur" id="utilisateur" class="form-control">
			<option value=""></option>
			<c:if test="${ !empty utilisateurs }">
				<c:forEach items="${ utilisateurs }" var="utilisateur">
					<option value="${ utilisateur.id }"><c:out value="${ utilisateur.nom } ${ utilisateur.prenom }" /></option>
				</c:forEach>
			</c:if>
		</select>
		<div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
        <input type="submit" class="btn btn-success" value="Valider" />
      	</div>
		</form>
      </div>
    </div>
  </div>
</div>
<br><br>

<table class="display table table-bordered" id="liste_indicateurs">
	<thead>
		<tr style="text-align: center;background-color: #7ED957;color: white;">
			<th>Catégorie</th>
			<th>Sous-Catégorie</th>
			<th>Libellé</th>
			<th>Unité</th>
			<th>Fréquence</th>
			<th>Responsable</th>
			<th>Service</th>
			<th>Statut</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${ !empty indicateurs }">
				<c:forEach items="${ indicateurs }" var="indicateur">
					<tr>
						<td>
							<c:out value="${ indicateur.libelle_categorie }" />
						</td>
						<td>
							<c:out value="${ indicateur.libelle_souscategorie }" />
						</td>
						<td>
							<c:out value="${ indicateur.libelle }" />
						</td>
						<td>
							<c:out value="${ indicateur.libelle_unite }" />
						</td>
						<td>
							<c:out value="${ indicateur.libelle_frequence }" />
						</td>
						<td>
							<c:out value="${ indicateur.nom_responsable } ${ indicateur.prenom_responsable }" />
						</td>
						<td>
							<c:out value="${ indicateur.libelle_service }" />
						</td>
						<td>
							<c:out value="${ indicateur.status }" />
						</td>
						<td>
							<a style="color: blue;" href="#" data-toggle="modal" data-target="#indicateurUpdate${ indicateur.id }"><i class="fa fa-edit" aria-hidden="true"></i></a> | 
							<c:if test="${ indicateur.status == 'on' }">
								<a style="color: red;" href="#" data-toggle="modal" data-target="#indicateurUnlock${ indicateur.id }"><i class="fa fa-unlock" aria-hidden="true"></i></a>
							</c:if>
							<c:if test="${ indicateur.status == 'off' }">
								<a style="color: red;" href="#" data-toggle="modal" data-target="#indicateurLock${ indicateur.id }"><i class="fa fa-lock" aria-hidden="true"></i></a>
							</c:if>
							<!-- Modal -->
							<div class="modal fade" id="indicateurUnlock${ indicateur.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLabel">Désactiver l'indicateur </h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          <span aria-hidden="true">&times;</span>
							        </button>
							      </div>
							      <div class="modal-footer">
							      <form method="post" action="indicateur">
							      	<input type="hidden" name="status_off" value="off" />
							      	<input type="hidden" name="id_indicateur_status" value="${ indicateur.id }" />
							      	<input type="button" class="btn btn-success" data-dismiss="modal" value="Non" />
							        <input type="submit" class="btn btn-danger" value="Oui" />
							      </form>
							      </div>
							    </div>
							  </div>
							</div>
							<!-- Modal -->
							<div class="modal fade" id="indicateurLock${ indicateur.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLabel">Activer l'indicateur </h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          <span aria-hidden="true">&times;</span>
							        </button>
							      </div>
							      <div class="modal-footer">
							      <form method="post" action="indicateur">
							      	<input type="hidden" name="status_on" value="on" />
							      	<input type="hidden" name="id_indicateur_status" value="${ indicateur.id }" />
							      	<input type="button" class="btn btn-success" data-dismiss="modal" value="Non" />
							        <input type="submit" class="btn btn-danger" value="Oui" />
							      </form>
							      </div>
							    </div>
							  </div>
							</div>
							
							<!-- Fenêtre modal pour la modification d'un indicateur -->
							<div class="modal fade" id="indicateurUpdate${ indicateur.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLabel">Modification d'un indicateur</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          <span aria-hidden="true">&times;</span>
							        </button>
							      </div>
							      <div class="modal-body">
							        <form method="post" action="indicateur">
							        	<label for="libelle">Libellé : </label>
							        	<input type="text" name="libelle_update" class="form-control" value="${ indicateur.libelle }" id="libelle" />
							        	<label>Code : </label>
							        	<input type="text" name="code_update" value="${ indicateur.code }" class="form-control" readonly />
							        	<label for="categorie">Categories : </label>
										<select name="id_categorie" id="categorie_update" class="form-control">
											<option value="${ indicateur.id_categorie }"><c:out value="${ indicateur.libelle_categorie }" /></option>
											<c:if test="${ !empty categories }">
												<c:forEach items="${ categories }" var="categorie">
													<option value="${ categorie.id }"><c:out value="${ categorie.libelle }" /></option>
												</c:forEach>
											</c:if>
										</select>
										<label>Sous-catégorie : </label>
										<select name="id_sous_categorie_update" id="souscategorie_update" class="form-control">
											<option value="${ indicateur.id_sous_categorie }"><c:out value="${ indicateur.libelle_souscategorie }" /></option>
											<c:if test="${ !empty souscategories }">
												<c:forEach items="${ souscategories }" var="souscategorie">
													<option value="${ souscategorie.id }" class="${ souscategorie.id }"><c:out value="${ souscategorie.libelle }" /></option>
												</c:forEach>
											</c:if>
										</select>
										<label for="frequence">Fréquence : </label>
										<select name="id_frequence_update" id="frequence" class="form-control">
											<option value="${ indicateur.id_frequence }"><c:out value="${ indicateur.libelle_frequence }" /></option>
											<c:if test="${ !empty frequences }">
												<c:forEach items="${ frequences }" var="frequence">
													<option value="${ frequence.id }"><c:out value="${ frequence.libelle }" /></option>
												</c:forEach>
											</c:if>
										</select>
										<label for="unite">Unité : </label>
										<select name="id_unite_update" id="categorie" class="form-control">
											<option value="${ indicateur.id_unite }"><c:out value="${ indicateur.libelle_unite }" /></option>
											<c:if test="${ !empty unites }">
												<c:forEach items="${ unites }" var="unite">
													<option value="${ unite.id }"><c:out value="${ unite.libelle }" /></option>
												</c:forEach>
											</c:if>
										</select>
										<label>Calculé ?</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" name="calcul_update" value="oui" /> Oui &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" name="calcul_update" value="non" /> Non<br>
										<label>Service : </label>
										<select name="id_service_update" id="service" class="form-control">
											<option value="${ indicateur.id_service }"><c:out value="${ indicateur.libelle_service }" /></option>
											<c:if test="${ !empty services }">
												<c:forEach items="${ services }" var="service">
													<option value="${ service.id }"><c:out value="${ service.libelle }" /></option>
												</c:forEach>
											</c:if>
										</select>
										<label>Responsable : </label>
										<select name="id_utilisateur_update" id="utilisateur" class="form-control">
											<option value="${ indicateur.id_utilisateur }"><c:out value="${ indicateur.prenom_responsable } ${ indicateur.nom_responsable }" /></option>
											<c:if test="${ !empty utilisateurs }">
												<c:forEach items="${ utilisateurs }" var="utilisateur">
													<option value="${ utilisateur.id }"><c:out value="${ utilisateur.nom } ${ utilisateur.prenom }" /></option>
												</c:forEach>
											</c:if>
										</select>
										<div class="modal-footer">
											<input type="hidden" name="tag_update" value="on" />
											<input type="hidden" name="status_indicateur" value="${ indicateur.status }" />
											<input type="hidden" name="id_indicateur_update" value="${ indicateur.id }" />
							        		<button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
							        		<button type="submit" class="btn btn-success">Enregistrer</button>
							      		</div>
							        </form>
							      </div>
							    </div>
							  </div>
							</div>
						</td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</tbody>
</table>

<script>

$(document).ready(function () {
   $('#liste_indicateurs').DataTable({

       language: {
       processing:     "Traitement en cours...",
       search:         "Rechercher&nbsp;:",
       lengthMenu:    "Afficher _MENU_ &eacute;l&eacute;ments",
       info:           "Affichage de l'&eacute;lement _START_ &agrave; _END_ sur _TOTAL_ &eacute;l&eacute;ments",
       infoEmpty:      "Affichage de l'&eacute;lement 0 &agrave; 0 sur 0 &eacute;l&eacute;ments",
       infoFiltered:   "(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)",
       infoPostFix:    "",
       loadingRecords: "Chargement en cours...",
       zeroRecords:    "Aucun &eacute;l&eacute;ment &agrave; afficher",
       emptyTable:     "Aucune donnée disponible dans le tableau",
       paginate: {
           first:      "Premier",
           previous:   "Pr&eacute;c&eacute;dent",
           next:       "Suivant",
           last:       "Dernier",
       },
       aria: {
           sortAscending:  ": activer pour trier la colonne par ordre croissant",
           sortDescending: ": activer pour trier la colonne par ordre décroissant"
       }
   },

   });
});

</script>
