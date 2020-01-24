<%@ include file="menu.jsp" %>
<br><br>
<strong style="font-size: 25px;">Gestion des sous-catégories</strong><br><br>
<c:if test="${ !empty erreur }">
	<c:out value="${ erreur }" />
</c:if>

<c:if test="${ erreur_suppression }">
	<c:out value="${ erreur_suppression }" />
</c:if>


<c:if test="${ message }">
<c:out value="${ message }" />
</c:if>
<br>
<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addSousModal" style="color: white;">
  Ajouter
</button>
<!-- Modal -->
<div class="modal fade" id="addSousModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">s
        <h5 class="modal-title" id="exampleModalLabel">Création d'une sous-catégorie</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" action="souscategorie">
		<label for="categorie">Categories : </label>
		<select name="id_categorie" id="categorie" class="form-control" required>
			<option value="">--- Séléctionner une catégorie ---</option>
			<c:if test="${ !empty categories }">
				<c:forEach items="${ categories }" var="categorie">
					<option value="${ categorie.id }"><c:out value="${ categorie.libelle }" /></option>
				</c:forEach>
			</c:if>
		</select>
		<label>Sous-catégorie : </label>
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
<table class="display table table-bordered" id="liste_categorie">
	<thead>
		<tr style="text-align: center;background-color: #7ED957;color: white;">
			<th>Catégorie</th>
			<th>Sous-catégorie</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${ !empty souscategories }">
			<c:forEach items="${ souscategories }" var="souscategorie">
			<tr>
				<td>
					<c:out value="${ souscategorie.libelle }" />
				</td>
				<td>
					<c:out value="${ souscategorie.libelle_categorie }" />
				</td>
				<td>
					<a data-toggle="modal" data-target="#updateSousModal${ souscategorie.id }" style="color: blue;"><i class="fa fa-edit" aria-hidden="true"></i></a>&nbsp; |
					<a data-toggle="modal" data-target="#suppSousModal${ souscategorie.id }" style="color: red;"><i class="fa fa-trash" aria-hidden="true"></i></a>
					
					<!-- Feneêtre modale de modification des sous-categorie -->
					<!-- Modal -->
					<div class="modal fade" id="updateSousModal${ souscategorie.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
					  <div class="modal-dialog" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="exampleModalLabel">Modification d'une sous-catégorie</h5>
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					          <span aria-hidden="true">&times;</span>
					        </button>
					      </div>
					      <div class="modal-body">
					       <form method="post" action="souscategorie" id="formUpdate">
					       <div class="form-group">
								<label for="categorie">Categories : </label>
								<select name="id_categorie_update" id="categorie" class="form-control" required>
									<option value="">--- Séléctionner une catégorie ---</option>
									<c:if test="${ !empty categories }">
										<c:forEach items="${ categories }" var="categorie">
											<option value="${ categorie.id }"><c:out value="${ categorie.libelle }" /></option>
										</c:forEach>
									</c:if>
								</select>
							</div>
							<div class="form-group">
									<input type="text" name="libelle_update" class="form-control" value="${ souscategorie.libelle }"/>
									<input type="hidden" name="id_update" value="${ souscategorie.id }" />
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
							<div class="modal fade" id="suppSousModal${ souscategorie.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLabel">Suppression</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          <span aria-hidden="true">&times;</span>
							        </button>
							      </div>
							      <div class="modal-body">
							        Êtes-vous sûr de vouloir supprimer la sous catégorie ?
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-success" data-dismiss="modal">Non</button>
							        <a href="souscategorie?id_delete=${ souscategorie.id }" class="btn btn-danger">Oui</a>
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
   $('#liste_categorie').DataTable({

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