<%@ include file="menu.jsp" %>

<br><br>
<strong style="font-size: 25px;">Données saisies</strong>
<br><br>
<c:if test="${ !empty message_ajout }">
	<div class="alert alert-success col-md-4" style="text-align: center;">
		<strong>
			<c:out value="${ message_ajout }" />
		</strong>
	</div>
</c:if>
<br><br>
	<table class="table display table-bordered" id="liste_saisies">
		<thead>
			<tr style="text-align: center;background-color: #7ED957;color: white;"">
				<th>Indicateur</th>
				<th>Valeur</th>
				<th>Date</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${ !empty saisies }">
				<c:forEach items="${ saisies }" var="saisie">
					<tr>
						<td>
							<c:out value="${ saisie.libelle_indicateur }" />
						</td>
						<form method="post" action="saisie" class="form-inline">
							<td>
								<c:choose>
								<c:when test="${ sessionScope.profil == 'Administrateur' || sessionScope.profil == 'Collaborateur' }">
									<input type="text" name="valeur" value="${ saisie.valeur }" class="form-control col-lg-4" />
								</c:when>
								<c:when test="${ sessionScope.profil == 'Lecteur' }">
									<input type="text" name="valeur" title="Vous n'avez pas les droits de modification" value="${ saisie.valeur }" readonly class="form-control col-lg-4" />
								</c:when>
								</c:choose>
								</td>
								<td>
									<c:out value="${ saisie.date_indicateur }" />
								</td>
								<input type="hidden" name="date_indicateur" value="${ saisie.date_indicateur }" class="datepicker" />
								<input type="hidden" name="id" value="${ saisie.id }" />
								<input type="hidden" name="id_indicateur" value="${ saisie.id_indicateur }" />
								<input type="hidden" name="tag_update" value="on" />
								<td>
								<c:choose>
									<c:when test="${ sessionScope.profil == 'Administrateur' || sessionScope.profil == 'Collaborateur' }">
										<input type="submit" value="Modifier" class="btn btn-info" />
									</c:when>
									<c:when test="${ sessionScope.profil == 'Lecteur' }">
										<input type="submit" value="Modifier" title="Vous n'avez pas les droits de modification" disabled class="btn btn-warning" />
									</c:when>
								</c:choose>
							</td>
						</form>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	
<script>
$(function() {
	$(".datepicker").datepicker();
});
$(document).ready(function () {
	   $('#liste_saisies').DataTable({

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