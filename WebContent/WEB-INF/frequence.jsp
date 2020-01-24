<%@ include file="menu.jsp" %>
<br><br>
<strong style="font-size: 25px;">Gestion des fréquences</strong><br><br>

<div>
	<c:if test="${ !empty erreur }">
		<p>
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
<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addFrevModal" style="color: white;">
  Ajouter
</button>
<br><br>
<!-- Modal -->
<div class="modal fade" id="addFrevModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Création d'une fréquence</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" action="frequence" id="formFrequence" class="form-group">
			<input type="text" class="form-control" name="libelle" id="libelle" placeholder="Saisir la frequence"/>
			<div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
		        <input type="submit" value="Valider" class="btn btn-success" />
		    </div>
		</form>
      </div>
    </div>
  </div>
</div>

<table class="display table table-bordered" id="liste_frequence">
	<thead>
		<tr style="text-align: center;background-color: #7ED957;color: white;">
			<th>Fréquence</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${ !empty frequences }">
			<c:forEach items="${ frequences }" var="frequence">
				<tr style="text-align: center;">
					<td>
						<c:out value="${ frequence.libelle }" />
					</td>
					<td>
						<a data-toggle="modal" data-target="#updateFrevModal${ frequence.id }" style="color: blue;"><i class="fa fa-edit" aria-hidden="true"></i></a>&nbsp; | &nbsp;
						<a data-toggle="modal" data-target="#suppFrevModal${ frequence.id }" style="color: red;"><i class="fa fa-trash" aria-hidden="true"></i></a>
						
						<!-- Fenetre modale de suppression d'une fréquence -->
						<div class="modal fade" id="suppFrevModal${ frequence.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
						  <div class="modal-dialog" role="document">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLabel">Suppression d'une fréquence</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          <span aria-hidden="true">&times;</span>
						        </button>
						      </div>
						      <div class="modal-body">
						        Êtes-vous sûr de vouloir supprimer la fréquence ?
						      </div>
						      <div class="modal-footer">
						        <button type="button" class="btn btn-success" data-dismiss="modal">Annuler</button>
						       <a href="frequence?id_delete=${ frequence.id }" class="btn btn-danger">Oui</a>
						      </div>
						    </div>
						  </div>
						</div>
						<!-- Fin fenêtre modale de supression d'une fréquence -->
						<!-- Fenêtre modale pour la modification d'une fréquence -->
						<div class="modal fade" id="updateFrevModal${ frequence.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
						  <div class="modal-dialog" role="document">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLabel">Modification d'une fréquence</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          <span aria-hidden="true">&times;</span>
						        </button>
						      </div>
						      <div class="modal-body">
						        <form method="post" action="frequence" id="formUpdate">
									<input type="text" class="form-control" name="libelle_update" value="${ frequence.libelle }"/>
									<input type="hidden" name="id_update" value="${ frequence.id }" />
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
						<!-- Fin de la modification d'une frequence -->
					</td>
				</tr>
			</c:forEach>
		</c:when>
	</c:choose>
	</tbody>
</table>
<script>
$(document).ready(function () {
   $('#liste_frequence').DataTable({

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
<script src="frequence.js"></script>