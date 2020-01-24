<%@ include file="menu.jsp" %>
<br>
<strong style="font-size: 25px;">Gestion des catégories</strong><br><br>
<div>
	<c:if test="${ !empty erreur }">
		<p class="alert alert-danger col-lg-4 offset-lg-4" role="alert" style="text-align: center;">
			<c:out value="${ erreur }" />
		</p>
	</c:if>
</div>

<div>
	<c:if test="${ !empty message }">
		<p class="alert alert-success col-lg-4 offset-lg-4" role="alert" style="text-align: center;">
			<c:out value="${ message }" />
		</p>
	</c:if>
</div>


<br>
<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addModal" style="color:white;">
  Ajouter
</button>
<!-- Modal -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Création d'une catégorie</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" action="categorie" id="formCategorie">
			<input type="text" name="libelle" id="libelle" class="form-control" required />
			<div class="modal-footer">
				<input type="submit" value="Valider" placeholder="Saisir la catégorie" class="btn btn-success" />
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
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
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${ !empty categories }">
				<c:forEach items="${ categories }" var="categorie">
					<tr style="text-align: center;">
						<td><c:out value="${ categorie.libelle }" /></td>
						<td>
							
							<a data-toggle="modal" data-target="#updateModal${ categorie.id }" style="color: blue;"><i class="fa fa-edit" aria-hidden="true"></i></a>&nbsp;|
							<a data-toggle="modal" data-target="#suppModal${ categorie.id }" style="color: red"><i class="fa fa-trash" aria-hidden="true"></i></a>
							
							<!-- Fênetre modale de modification -->
							<div class="modal fade" id="updateModal${ categorie.id }" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel${ categorie.id }" aria-hidden="true">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="updateModalLabel${ categorie.id }">Modification d'une categorie</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          <span aria-hidden="true">&times;</span>
							        </button>
							      </div>
							      <div class="modal-body">
							      	<form method="post" action="categorie" id="formCatUpdate">
							        	<input type="text" class="form-control" name="libelle_update" value="${ categorie.libelle }" />
										<input type="hidden" name="id_update" value="${ categorie.id }" />
										<input type="hidden" name="tag_update" value="on"/>
										<div class="modal-footer">
									        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
									        <input type="submit" class="btn btn-success" value="Valider" />
									     </div>
									</form>
							      </div>
							    </div>
							  </div>
							</div>
							<!-- Fin pour la modification -->
							
							<!-- Fênetre modale pour la suppression -->
							<div class="modal fade" id="suppModal${ categorie.id }" tabindex="-1" role="dialog" aria-labelledby="suppModal${ categorie.id }" aria-hidden="true">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLabel">Suppression d'une catégorie</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          <span aria-hidden="true">&times;</span>
							        </button>
							      </div>
							      <div class="modal-body">
							        Êtes-vous sûr de vouloir supprimer la catégorie ?
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-success" data-dismiss="modal">Non</button>
							        <a href="categorie?id_delete=${ categorie.id }" class="btn btn-danger">Oui</a>
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
    $('input:first').focus();
    
 });
</script>
<script src="categorie.js"></script>


