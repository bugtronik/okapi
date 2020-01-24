<%@ include file="menu.jsp" %>
<br><br>
<strong style="font-size: 25px;">Gestion des comptes</strong>
<br><br>
<c:if test="${ !empty erreur }">
	<c:out value="${ erreur }" />
</c:if>

<c:if test="${ erreur_suppression }">
	<c:out value="${ erreur_suppression }" />
</c:if>

<c:if test="${ message }">
<c:out value="${ message }" />
</c:if>

<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addUserModal" style="color: white;">
	Ajouter
</button>
<br><br>
<!-- Modal -->
<div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Ajout d'un utilisateur</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" action="utilisateur">
		<label for="categorie">Profil : </label>
		<select name="id_profil" id="profil" class="form-control">
			<option value="">--- Séléctionner un profil ---</option>
			<c:if test="${ !empty profils }">
				<c:forEach items="${ profils }" var="profil">
					<option value="${ profil.id }"><c:out value="${ profil.libelle }" /></option>
				</c:forEach>
			</c:if>
		</select>
		<label>Nom : </label>
		<input type="text" name="nom" autocomplete="off" class="form-control" />
		<label>Prénom : </label>
		<input type="text" name="prenom" autocomplete="off" class="form-control" />
		<label for="username">Nom d'utilisateur : </label>
		<input type="text" name="username" autocomplete="off" class="form-control" />
		<label>Mot de passe : </label>
		<input type="password" name="password" class="form-control" />
		<label>Confirmer : </label>
		<input type="password" name="password_confirm" class="form-control" />
		<div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
        <input type="submit" class="btn btn-success" value="Valider" />
      </div>
	</form>
      </div>
    </div>
  </div>
</div>
<br>
<table class="display table table-bordered" id="liste_utilisateurs">
	<thead>
		<tr style="text-align: center;background-color: #7ED957;color: white;">
			<td>Nom</td>
			<td>Prénom</td>
			<td>Nom d'utilisateur</td>
			<td>Profil</td>
			<td>Action</td>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${ !empty utilisateurs }">
			<c:forEach items="${ utilisateurs }" var="utilisateur">
			<tr>
				<td>
					<c:out value="${ utilisateur.nom }" />
				</td>
				<td>
					<c:out value="${ utilisateur.prenom }" />
				</td>
				<td>
					<c:out value="${ utilisateur.username }" />
				</td>
				<td>
					<c:out value="${ utilisateur.libelle_profil }" />
				</td>
				<td>
					<a data-toggle="modal" data-target="#updateSousModal${ utilisateur.id }" style="color: blue;" title="Modifier le compte"><i class="fa fa-edit" aria-hidden="true"></i></a>&nbsp;
					
					<!-- Feneêtre modale de modification des sous-categorie -->
					<!-- Modal -->
					<div class="modal fade" id="updateSousModal${ utilisateur.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
					  <div class="modal-dialog" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="exampleModalLabel">Modification d'un utilisateur</h5>
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					          <span aria-hidden="true">&times;</span>
					        </button>
					      </div>
					      <div class="modal-body">
					       <form method="post" action="utilisateur" id="formUpdate">
					       <div class="form-group">
								<label for="profil">Profil : </label>
								<select name="id_profil_update" id="profil" class="form-control">
									<option value="">--- Séléctionner un profil ---</option>
									<c:if test="${ !empty profils }">
										<c:forEach items="${ profils }" var="profil">
											<option value="${ profil.id }"><c:out value="${ profil.libelle }" /></option>
										</c:forEach>
									</c:if>
								</select>
							</div>
							<div class="form-group">
								<label>Nom : </label>
								<input type="text" name="nom_update" autocomplete="off" value="${ utilisateur.nom }" class="form-control" />
								<label>Prénom : </label>
								<input type="text" name="prenom_update" autocomplete="off" value="${ utilisateur.prenom }" class="form-control" />
								<label for="username">Nom d'utilisateur : </label>
								<input type="text" name="username_update" value="${ utilisateur.username }" autocomplete="off" class="form-control" />
								<label>Mot de passe : </label>
								<input type="password" name="password_update" class="form-control" />
								<label>Confirmer : </label>
								<input type="password" name="password_update_confirm" class="form-control" />
								<input type="hidden" name="id_update" value="${ utilisateur.id }" />
								<input type="hidden" name="tag_update" value="on" />
								<div class="modal-footer">
					        		<button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
					        		<input type="submit" value="Valider" class="btn btn-success" />
					      		</div>
					      	</div>
							</form>
					      </div>
					    </div>
					  </div>
					</div>
					<!-- Fin de la fenêtre modale de modification des sous-categories -->
				</td>
				</tr>
			</c:forEach>
		</c:when>
	</c:choose>
	</tbody>
</table>

<script>
$(document).ready(function () {
   $('#liste_utilisateurs').DataTable({

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
$('.modal').on('shown.bs.modal', function(){
    $('input:first').focus()
 });
</script>