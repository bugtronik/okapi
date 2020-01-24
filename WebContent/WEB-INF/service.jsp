<%@ include file="menu.jsp" %>
<br><br>
<strong style="font-size: 25px;">Gestion des services</strong><br><br>
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
<button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addServModal" style="color:white;">
  Ajouter
</button>
<!-- Modal -->
<div class="modal fade" id="addServModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Création d'un service</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" action="service" id="formService">
			<input type="text" required class="form-control" name="libelle" id="libelle" placeholder="Saisir l'unité"/>
			<div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
		        <input type="submit" value="Valider" class="btn btn-success" />
		     </div>
		</form>
      </div>
    </div>
  </div>
</div>

<br><br>
<table class="display table table-bordered" id="liste_service">
	<thead>
		<tr style="text-align: center;background-color: #7ED957;color: white;">
			<th>Services</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
			<c:choose>
				<c:when test="${ !empty services }">
					<c:forEach items="${ services }" var="service">
					<tr>
						<td>
							<c:out value="${ service.libelle }" />
						</td>
						<td>
							<a data-toggle="modal" data-target="#updateServModal${ service.id }" href="service?id_service=${ service.id }" style="color: blue; text-align: right;"><i class="fa fa-edit" aria-hidden="true"></i></a> | &nbsp;
							<a data-toggle="modal" data-target="#suppServModal${ service.id }" style="color: red;text-align: right;"><i class="fa fa-trash" aria-hidden="true"></i></a>
							
							<!-- Fenêtre modal pour la suppression d'un service -->
							<div class="modal fade" id="suppServModal${ service.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLabel">Suppression d'un service</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          <span aria-hidden="true">&times;</span>
							        </button>
							      </div>
							      <div class="modal-body">
							        Êtes-vous sûr de vouloir supprimer l'unité ?
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-success" data-dismiss="modal">Non</button>
							        <a href="service?id_delete=${ service.id }" class="btn btn-danger">Oui</a>
							      </div>
							    </div>
							  </div>
							</div>
							<!-- Fin de la fenêtre modale de suppression de services -->
							
							<!-- Modal pour la modification d -->
							<div class="modal fade" id="updateServModal${ service.id }" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLabel">Modification d'un service</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          <span aria-hidden="true">&times;</span>
							        </button>
							      </div>
							      <div class="modal-body">
							       <form method="post" action="service" id="formUpdate">
										<input type="text" class="form-control" name="libelle_update" value="${ service.libelle }"/>
										<input type="hidden" name="id_update" value="${ service.id }" />
										<input type="hidden" name="tag_update" value="on"/><br>
										<div class="modal-footer">
											<button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
										    <input type="submit" class="btn btn-success" value="Valider" />
										</div>
									</form>
							      </div>
							    </div>
							  </div>
							</div>
							<!-- Fin de la fenêtre modale de modification d'un service -->
						</td>
					</tr>
					</c:forEach>
				</c:when>
			</c:choose>
	</tbody>
</table>

<script>
$(document).ready(function () {
   $('#liste_service').DataTable({

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
<script src="service.js"></script>