<%@ include file="menu.jsp" %>
<br>
<strong style="font-size: 25px;">Exportation d'indicateurs</strong>
<br><br>

<form method="post" action="exportcsv">
	<div class="row">
		<div class="col-md-5">
			<label><strong>Date début : </strong></label>
			<input type=text name="date_debut" class="form-control datepicker" placeholder="Date de début" autocomplete="off" required />
		</div>
		<div class="col-md-5">
			<label><strong>Date fin : </strong></label>
			<input type=text name="date_fin" class="form-control datepicker" placeholder="Date de fin" autocomplete="off" required />
		</div>
	</div><br>
	<div class="row">
		<div class="col">
			<input type="submit" value="Exporter" class="btn btn-success col-md-2" style="color: white;font-weight: bold;" />
		</div>
	</div>
</form><br /><br />

<c:if test="${ !empty exportation }">
	<a href="http://localhost:8080/kpi/fichiers/exports.xls">Télécharger le fichier</a>
</c:if>

<script>
$( function() {
	
    $( ".datepicker" ).datepicker({ dateFormat: 'yy-mm-dd' });
});
</script>