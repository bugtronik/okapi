<%@ include file="menu.jsp" %>
<br>
<strong style="font-size: 25px;">Import des objectifs</strong>
<br><br>

<form method="post" action="import">
	<div class="row">
		<div class="col-md-4">
			<label><strong>Contr�leur : </strong></label>
			<input type=text name="username" class="form-control" value="<c:out value="${ username }" />" readonly />
		</div>
		<div class="col-md-4">
			<label><strong>Service : </strong></label>
			<select name="id_service" class="form-control" id="services">
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
			<select name="id_indicateur" class="form-control" id="indicateurs">
				<option value=""></option>
				<c:if test="${ !empty indicateurs }">
					<c:forEach items="${ indicateurs }" var="indicateur">
						<option value="${ indicateur.id }" class="${ indicateur.id_service }"><c:out value="${ indicateur.libelle }" /></option>
					</c:forEach>
				</c:if>
			</select>
		</div>
	</div><br>
	<div class="row">
		<div class="col">
			<input type="submit" value="G�n�rer le fichier" class="btn btn-success col-md-4" style="color: white;font-weight: bold;" />
		</div>
	</div>
</form><br /><br />

<script>
	$( function () {
		$("#indicateurs").chained("#services");
	});
</script>