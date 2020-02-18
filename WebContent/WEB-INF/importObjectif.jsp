<%@ include file="menu.jsp" %>
<br><br>

<form method="post" action="exportcsv">
	<div class="row">
		<div class="col-md-4">
			<label><strong>Contrôleur : </strong></label>
			<input type=text name="username" class="form-control" value="<c:out value="${ username }" />" readonly />
		</div>
		<div class="col-md-4">
			<label><strong>Service : </strong></label>
			<select name="id_service" class="form-control">
				<option value=""></option>
				<c:if test="${ !empty services }">
					<c:forEach items="${ services }" var="service">
						<option value="${ service.id }"><c:out value="${ service.libelle }" /></option>
					</c:forEach>
				</c:if>
			</select>
		</div>
		<div class="col-md-4">
			<label><strong>Indicateur : </strong></label>
			<select name="id_indicateur" class="form-control">
				<option value=""></option>
			</select>
		</div>
	</div><br>
	<div class="row">
		<div class="col">
			<input type="submit" value="Générer le fichier" class="btn btn-success col-md-4" style="color: white;font-weight: bold;" />
		</div>
	</div>
</form><br /><br />