<html>
<head>
	<!-- include primefaces stylesheets -->
	<link type="text/css" rel="stylesheet" href="/asistente-eventos/javax.faces.resource/themes/sam/theme.css.jsf?ln=primefaces&amp;amp;v=2.2.1" />
	<script type="text/javascript" src="/asistente-eventos/javax.faces.resource/jquery/jquery.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<script type="text/javascript" src="/asistente-eventos/javax.faces.resource/core/core.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<script type="text/javascript" src="/asistente-eventos/javax.faces.resource/themeswitcher/themeswitcher.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<link type="text/css" rel="stylesheet" href="/asistente-eventos/javax.faces.resource/jquery/ui/jquery-ui.css.jsf?ln=primefaces&amp;v=2.2.1" />
	<link type="text/css" rel="stylesheet" href="/asistente-eventos/javax.faces.resource/wijmo/wijmo.css.jsf?ln=primefaces&amp;v=2.2.1" />
	<script type="text/javascript" src="/asistente-eventos/javax.faces.resource/jquery/ui/jquery-ui.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<script type="text/javascript" src="/asistente-eventos/javax.faces.resource/wijmo/wijmo.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<script type="text/javascript" src="/asistente-eventos/javax.faces.resource/menu/menu.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<link type="text/css" rel="stylesheet" href="/asistente-eventos/javax.faces.resource/toolbar/toolbar.css.jsf?ln=primefaces&amp;v=2.2.1" />
	<script type="text/javascript" src="/asistente-eventos/javax.faces.resource/button/button.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<script type="text/javascript" src="/asistente-eventos/javax.faces.resource/confirmdialog/confirmdialog.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<link type="text/css" rel="stylesheet" href="/asistente-eventos/javax.faces.resource/fieldset/fieldset.css.jsf?ln=primefaces&amp;v=2.2.1" />
	<script type="text/javascript" src="/asistente-eventos/javax.faces.resource/fieldset/fieldset.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<link type="text/css" rel="stylesheet" href="/asistente-eventos/javax.faces.resource/messages/messages.css.jsf?ln=primefaces&amp;v=2.2.1" />
	<link type="text/css" rel="stylesheet" href="/asistente-eventos/javax.faces.resource/growl/assets/growl.css.jsf?ln=primefaces&amp;v=2.2.1" />
	<script type="text/javascript" src="/asistente-eventos/javax.faces.resource/growl/growl.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<script type="text/javascript" src="/asistente-eventos/javax.faces.resource/ajaxstatus/ajaxstatus.js.jsf?ln=primefaces&amp;v=2.2.1"></script>
	<!-- End include primefaces stylesheets  -->

	<script type="text/javascript">
		function showDiv() {
			var visibility = document.getElementById("login-config").style.visibility;
			if(visibility == "") {
				document.getElementById("login-config").style.visibility = "hidden";
			} else {
				document.getElementById("login-config").style.visibility = "";
			}
		}
	</script>
	<title>Login</title>
</head>
<body style="background-color:#f1f1f1" onload="document.getElementById('j_idt21').focus();">
	<table style="width: 100%">
		<tr>
			<td height="70" width="100%" align="left"><div id="j_idt6"></div></td>
		</tr>
		<tr>
			<td height="20" colspan="2">
				<div id="j_idt8:j_idt9">
					<ul id="j_idt8:j_idt9_menu">
						<p style="text-align:center;font-weight:400;">
							<span class="wijmo-wijmenu-text"><font color="#555555">LOGIN</font></span>
						</p>
					</ul>
				</div><script type="text/javascript">widget_j_idt8_j_idt9 = new PrimeFaces.widget.Menubar('j_idt8:j_idt9',{autoSubmenuDisplay:false,animated:'fade'});</script>
				<input type="hidden" name="javax.faces.ViewState" id="javax.faces.ViewState" value="4535356767077170554:-2328445260162652618" autocomplete="off" />
			</td>
		</tr>		
		
		<tr valign="top">
			<td height="150" colspan="2" align="center">				 
				<br/><br/>				
				<div class="ui-widget ui-fieldset-content">
				<table id="error" align="center">
					<tbody>
						<tr>
							<td>Username or password incorrect</td>
						</tr>
						<tr>
							<td>
								<div class="ui-toolbar-group-right">
									<button id="j_idt21" name="j_idt21" onclick=" location.href='/asistente-eventos/index.jsf' ">Back</button>									
									<script type="text/javascript">widget_j_idt21 = new PrimeFaces.widget.CommandButton('j_idt21', {});</script>
								</div>
							</td>
						</tr>  
					</tbody>  
				</table>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<a href="javascript:showDiv()">Make sure you already have included this Policy in your JBoss Server Login Configuration file (login-config.xml)</a>				
				<div id="login-config" style="visibility:hidden;">
				  <textarea rows="15" cols="100" readonly>
<application-policy name="JAAS">  
  <authentication>  
    <login-module code="org.jboss.security.auth.spi.DatabaseServerLoginModule" flag="required">  
      <module-option name="dsJndiName">java:/DefaultDS</module-option>  
      <module-option name="principalsQuery">select usuario0_.pwd from Usuario usuario0_ where usuario0_.username=?</module-option>  
      <module-option name="rolesQuery">select rol0_.descripcion, 'Roles' from Usuario usuario0_,Rol rol0_ where usuario0_.rolId=rol0_.rolId and usuario0_.username=?</module-option>
      <module-option name="hashAlgorithm">MD5</module-option>  
      <module-option name="hashEncoding">hex</module-option>
    </login-module>  
  </authentication>  
</application-policy>
				  </textarea>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>
