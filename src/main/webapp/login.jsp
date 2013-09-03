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
	
	<title>Login</title>
</head>
	<body style="background-color:#f1f1f1" onload="document.j_security_check.username.focus();">
	<table style="width: 100%">
		
		<!-- Header Text  -->
		<tr>
			<td height="70" width="100%" align="left"><div id="j_idt6"></div></td>
		</tr>
		<tr>
			<td height="20" colspan="2">
				<div id="j_idt8:j_idt9">
					<ul id="j_idt8:j_idt9_menu">
						<p style="text-align:center;">
							<span class="wijmo-wijmenu-text"><font color="#555555">LOGIN</font></span>
						</p>
					</ul>
				</div><script type="text/javascript">widget_j_idt8_j_idt9 = new PrimeFaces.widget.Menubar('j_idt8:j_idt9',{autoSubmenuDisplay:false,animated:'fade'});</script>
				<input type="hidden" name="javax.faces.ViewState" id="javax.faces.ViewState" value="4535356767077170554:-2328445260162652618" autocomplete="off" />
			</td>
		</tr>
		<!-- End Header Text  -->
		 
		<tr valign="top">
			<td height="400" colspan="2" align="center">
				<div id="layout" >  
					
					<!-- JAAS Login Form  -->
					<form method="post" action="j_security_check" name="j_security_check">  
						<br/><br/>
						
						<div class="ui-widget ui-fieldset-content">
						<table id="loginTable" align="center">
							<tbody>
								<tr>
									<td><label for="username" class="text-input">Username: </label></td>
									<td>
										<input id="username" type="text" name="j_username" title="Username"/>										
									</td>	
								</tr>
								<tr>
									<td><label for="password" class="text-input">Password: </label></td>
									<td><input id="password" type="password" name="j_password" title="Password" /></td>
								</tr>								
								<tr><td></td>
									<td>
										<div class="ui-toolbar-group-right">
											<button id="j_idt21" name="j_idt21" onclick=";" type="submit">Accept</button>									
											<script type="text/javascript">widget_j_idt21 = new PrimeFaces.widget.CommandButton('j_idt21', {});</script>
										</div>
									</td>
								</tr>								
								<tr>								
									<td colspan="2"><font color="green" size="2">* Default User/Pass: "user / user", "admin / admin"</font></td>
								</tr>
							</tbody>  
						</table>
						</div>
					</form>  
					<!-- End JAAS Login Form  -->
					
				</div>
			</td>
		</tr>
	</table>
	</body>
</html>