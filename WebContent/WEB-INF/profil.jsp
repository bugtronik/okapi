<%@ include file="menu.jsp" %>
<br><br>
<strong style="font-size: 25px;">Gestion des profils</strong><br><br>
<c:if test="${ !empty erreur }">
	<p class="alert alert-danger col-lg-6 offset-lg-3" role="alert" style="text-align: center;">
		<strong><c:out value="${ erreur }" /></strong>
	</p>
</c:if>

<c:if test="${ erreur_suppression }">
	<p class="alert alert-danger col-lg-4 offset-lg-4" role="alert" style="text-align: center;">
		<strong><c:out value="${ erreur_suppression }" /></strong>
	</p>
</c:if>

<c:if test="${ message }">
	<p class="alert alert-success col-lg-4 offset-lg-4" style="text-align: center;" role="alert">
		<c:out value="${ message }" />
	</p>
</c:if>

<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addProfilModal" style="color: white;">
	Ajouter
</button>
<!-- Modal -->
<div class="modal fade" id="addProfilModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Création d'un profil</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" action="profil">
		<label for="categorie">Privilèges : </label>
		<select name="id_categorie" id="categorie" class="form-control">
			<option value="">--- Séléctionner un privilège ---</option>
			<c:if test="${ !empty privileges }">
				<c:forEach items="${ privileges }" var="privilege">
					<option value="${ privilege.id }"><c:out value="${ privilege.libelle }" /></option>
				</c:forEach>
			</c:if>
		</select>
		<label>Profil : </label>
		<input type="text" name="libelle" autocomplete="off" class="form-control" />
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
<table class="display table" id="liste_profil">
	<thead>
		<tr style="text-align: center;background-color: #7ED957;color: white;">
			<th>Profil</th>
			<th>Privilège</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${ !empty profiles }">
			<c:forEach items="${ profiles }" var="profil">
			<tr>
				<td>
					<c:out value="${ profil.libelle }" />
				</td>
				<td>
					<c:out value="${ profil.libelle_privilege }" />
				</td>
				<td>
					<a data-toggle="modal" data-target="#updateProModal${ profil.id }" style="color: blue;" title="Modifier le profil"><i class="fa fa-edit" aria-hidden="true"></i></a>
					<!-- <a data-toggle="modal" data-target="#suppProModal${ profil.id }" style="color: red;">Supprimer</a> -->
					
					<!-- Feneêtre modale de modification des sous-categorie -->
					<!-- Modal -->
					<div class="modal fade" id="updateProModal${ profil.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
					  <div class="modal-dialog" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="exampleModalLabel">Modification d'un profil</h5>
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					          <span aria-hidden="true">&times;</span>
					        </button>
					      </div>
					      <div class="modal-body">
					       <form method="post" action="profil" id="formUpdate">
					       <div class="form-group">
								<label for="categorie">Privilèges : </label>
								<select name="id_privilege_update" id="privilege" class="form-control">
									<option value="">--- Séléctionner un privilège ---</option>
									<c:if test="${ !empty privileges }">
										<c:forEach items="${ privileges }" var="privilege">
											<option value="${ privilege.id }"><c:out value="${ privilege.libelle }" /></option>
										</c:forEach>
									</c:if>
								</select>
							</div>
							<div class="form-group">
									<input type="text" name="libelle_update" class="form-control" value="${ profil.libelle }"/>
									<input type="hidden" name="id_update" value="${ profil.id }" />
									<input type="hidden" name="tag_update" value="on"/>
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
					
					<!-- Modal pour la suppression des sous-categories -->
							<!-- Modal -->
							<div class="modal fade" id="suppProModal${ profil.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLabel">Modification d'un profil</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          <span aria-hidden="true">&times;</span>
							        </button>
							      </div>
							      <div class="modal-body">
							        Êtes-vous sûr de vouloir supprimer le profil ?
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-success" data-dismiss="modal">Non</button>
							        <a href="profil?id_delete=${ profil.id }" class="btn btn-danger">Oui</a>
							      </div>
							    </div>
							  </div>
							</div>
							<!-- Fin modal pour la suppression des sous-categories -->
				</td>
				</tr>
			</c:forEach>
		</c:when>
	</c:choose>
	</tbody>
</table>
<script>
$(document).ready(function () {
   $('#liste_profil').DataTable({

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
