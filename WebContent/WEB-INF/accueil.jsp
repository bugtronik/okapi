<%@ include file="menu.jsp" %>
<br><br><br>


<h4>Indicateur au &nbsp;&nbsp;<strong><c:out value="${ date_precedente }" /></strong></h4>
<br>
<table class="table table-bordered table-hover" id="liste_saisies">
  <thead style="text-align: center;background-color: #7ED957;color: white;">
    <tr>
      <th scope="col">Activités</th>
      <th scope="col">Unité</th>
      <th scope="col">Fréquence</th>
      <th scope="col">Réel</th>
      <th scope="col">Objectif</th>
    </tr>
  </thead>
  <tbody>
  	<c:choose>
		<c:when test="${ !empty categories }">
			<c:forEach items="${ categories }" var="categorie">
				<tr style="background-color: #FFCD05;color: #38B6FF;">
					<td colspan="5">
						<strong><c:out value="${ categorie.libelle }" /></strong>
					</td>
				</tr>
				<c:forEach items="${ souscategories }" var="souscategorie"> 
					<c:if test="${ categorie.id == souscategorie.id_categorie }">
						<tr>
							<td colspan="5" style="color: #38B6FF;">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-circle" aria-hidden="true"></i>
								<c:out value="${ souscategorie.libelle }" />
								<c:forEach items="${ indicateurs }" var="indicateur">
								<c:if test="${ indicateur.id_sous_categorie == souscategorie.id }">
									<tr>
										<td>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${ indicateur.libelle }" />
										</td>
										<td>
											<c:out value="${ indicateur.libelle_unite }" />
										</td>
										<td>
											<c:out value="${ indicateur.libelle_frequence }" />
										</td>
										<td style="text-align: center;">
											<c:choose>
											<c:when test="${ indicateur.version == 'reelle'}">
												<c:out value="${ indicateur.valeur_indicateur }" />
											</c:when>
											<c:otherwise>
												<i class="fa fa-times" aria-hidden="true"></i>
											</c:otherwise>
											</c:choose>
										</td>
										<td style="text-align: center;">
											<c:forEach items="${ objectifs }" var="objectif">
												<c:choose>
												<c:when test="${ objectif.version == 'objectif' and indicateur.id == objectif.id_indicateur and objectif.date_indicateur == date_saisie }">
													<c:if test="${ objectif.valeur > indicateur.valeur_indicateur }">
														<strong><c:out value="${ objectif.valeur }" /></strong>
													</c:if>
													<c:if test="${ objectif.valeur == indicateur.valeur_indicateur ||  objectif.valeur < indicateur.valeur_indicateur}">
														<strong class="text-success"><c:out value="${ objectif.valeur }" /></strong>
													</c:if>
												</c:when>
												</c:choose>
											</c:forEach>
										</td>
									</tr>
								</c:if>
					</c:forEach>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:forEach>
		</c:when>
	</c:choose>
  </tbody>
</table>
<!-- 
												<c:forEach items="${ objectifs }" var="objectif">
													<c:choose>
													<c:when test="${ objectif.version == 'objectif' and indicateur.id == objectif.id_indicateur and objectif.date_indicateur == date_saisie }">
														<c:choose>
														<c:when test="${ indicateur.valeur_indicateur > objectif.valeur || indicateur.valeur_indicateur == objectif.valeur }">
															<strong style="color: green;"><c:out value="${ indicateur.valeur_indicateur }" /></strong>
														</c:when>
														<c:when test="${ indicateur.valeur_indicateur < objectif.valeur  }">
															<strong style="color: red;"><c:out value="${ indicateur.valeur_indicateur }" /></strong>
														</c:when>
														</c:choose>
													</c:when>
													</c:choose>
												</c:forEach>
												 -->