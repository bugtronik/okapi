<%@ include file="menu.jsp" %>
<br>
<strong style="font-size: 25px;">Liste des objectifs</strong><br><br>
	<table class="table table-bordered display" id="liste_saisies">
		<thead>
			<tr style="text-align: center;background-color: #7ED957;color: white;"">
				<th>Indicateur</th>
				<th>Date</th>
				<th>Version</th>
				<th>Valeur</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${ !empty objectifs }">
				<c:forEach items="${ objectifs }" var="objectif">
					<tr>
						<td>
							<c:out value="${ objectif.libelle_indicateur }" />
						</td>
						<td>
							<c:out value="${ objectif.date_indicateur }" />
						</td>
						<td>
							<c:out value="${ objectif.version }" />
						</td>
						<td>
							<c:out value="${ objectif.valeur }" />
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	
<script>

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
</script>