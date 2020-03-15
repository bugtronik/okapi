<%@ include file="menu.jsp" %>
<br>
<strong style="font-size: 25px;">Mes indicateurs</strong>
<br><br>
<div class="alert alert-success" role="alert" style="display: none;text-align: center;" id="message_success">
	<strong>Opération terminée</strong>
</div>
<c:if test="${ !empty erreur }">
	<div class="alert alert-danger offset-lg-4 message_error" style="text-align: center;">
		<strong>
			<c:out value="${ erreur }" />
		</strong>
	</div>
</c:if>
	<c:if test="${ !empty message_ajout }">
	<div class="alert alert-success" style="text-align: center;">
		<strong>
			<c:out value="${ message_ajout }" />
		</strong>
	</div>
</c:if>
<br><br>
<c:if test="${ !empty message_erreur }">
	<div class="alert alert-danger" style="text-align: center;">
		<strong>
			<c:out value="${ message_erreur }" />
		</strong>
	</div>
</c:if>
	<table class="table display table-bordered" id="liste_indicateurs">
		<thead>
			<tr style="text-align: center;background-color: #7ED957;color: white;">
				<th>Indicateur</th>
				<th>Unité</th>
				<th>Fréquence</th>
				<th>Date</th>
				<th>Valeur</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody class="table-hover">
			<!-- 
			<c:choose>
				<c:when test="${ !empty verifications }">
					<c:forEach items="${ verifications }" var="verification">
						<c:choose>
							<c:when test="${ !empty indicateurs }">
								<c:forEach items="${ indicateurs }" var="indicateur">
								
									<c:if test="${ verification.id_indicateur == indicateur.id and verification.date_indicateur == date_actuelle }">
									<tr style="text-align: center;">
										<td>
											<c:out value="${ indicateur.libelle }" />
										</td>
										<td>
											<c:out value="${ indicateur.libelle_unite }" />
										</td>
										<td>
											<c:out value="${ indicateur.libelle_frequence }" />
										</td>
											<form method="post" action="saisie" class="form-inline formSaisie" style="text-align: center;">
												<div class="form-group">
													<td>
														<input type=text name="date_indicateur" class="form-control datepicker saisies" placeholder="Date" autocomplete="off" required />&nbsp;
													</td>
													<td>
														<input type="text" step="any" name="valeur" class="form-control saisie" placeholder="Valeur" autocomplete="off" />&nbsp;
													</td>
													<input type="hidden" name="id_indicateur" value="${ indicateur.id }" />
													<input type="hidden" value="reelle" name="version" />
													<input type="hidden" name="id_utilisateur" value="${ indicateur.id_utilisateur }" />
													<input type="hidden" name="date_indicateur" />
													<td>
														<input type="submit" value="Valider" class="btn btn-success valider" data-toggle="tooltip" data-placement="right" title="La valeur sera enregistrer automatiquement aprï¿½s click" />&nbsp;
														<div class="user_message1"></div>
													</td>
												</div>
											</form>
									</tr>
									
								</c:if>
								</c:forEach>
							</c:when>
						</c:choose>
					</c:forEach>
				</c:when>
			</c:choose>
			-->
			
			<c:choose>
				<c:when test="${ !empty indicateurs }">
					<c:forEach items="${ indicateurs }" var="indicateur">
					<tr style="text-align: center;">
						<td>
							<c:out value="${ indicateur.libelle }" />
						</td>
						<td>
							<c:out value="${ indicateur.libelle_unite }" />
						</td>
						<td>
							<c:out value="${ indicateur.libelle_frequence }" />
						</td>
							<form method="post" action="saisie" class="form-inline formSaisie" style="text-align: center;">
								<div class="form-group">
									<td>
										<input type=text name="date_indicateur" class="form-control datepicker saisies" placeholder="Date" autocomplete="off" required />&nbsp;
									</td>
									<td>
										<c:if test="${ indicateur.libelle_unite == 'Heure' }">
											<input type="hidden" name="unite" value="Heure" />
											<input type="time" step="1" name="valeur" class="form-control" placeholder="Valeur" autocomplete="off" />&nbsp;
										</c:if>
										<c:if test="${ indicateur.libelle_unite != 'Heure' }">
											<input type="text" step="any" name="valeur" class="form-control saisie" placeholder="Valeur" autocomplete="off" />&nbsp;
										</c:if>
									</td>
									<input type="hidden" name="id_indicateur" value="${ indicateur.id }" />
									<input type="hidden" value="reelle" name="version" />
									<input type="hidden" name="id_utilisateur" value="${ indicateur.id_utilisateur }" />
									<input type="hidden" name="date_indicateur" />
									<td>
										<input type="submit" value="Valider" class="btn btn-success valider" data-toggle="tooltip" data-placement="right" title="La valeur sera enregistrer automatiquement aprï¿½s click" />&nbsp;
										<div class="user_message1"></div>
									</td>
								</div>
							</form>
					</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		
		</tbody>
	</table><br><br>
	
  <script src="saisie.js"></script>
 <script>
 
$( function() {
	$( ".datepicker" ).datepicker({ dateFormat: 'yy-mm-dd' });
});
 
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
	       emptyTable:     "Aucune donnï¿½e disponible dans le tableau",
	       paginate: {
	           first:      "Premier",
	           previous:   "Pr&eacute;c&eacute;dent",
	           next:       "Suivant",
	           last:       "Dernier",
	       },
	       aria: {
	           sortAscending:  ": activer pour trier la colonne par ordre croissant",
	           sortDescending: ": activer pour trier la colonne par ordre dï¿½croissant"
	       }
	   },

	   });
	});
 </script>
