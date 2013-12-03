<%-- 
    Document   : showPhotos
    Created on : 18/10/2013, 06:14:07 PM
    Author     : gabriela.rosales
--%>

<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.social.Photo"%>
<%@page import="org.semanticwb.social.Message"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.PostOut"%>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=x-iso-8859-11">
    </head>
    <body>
<%
    String pOut = request.getParameter("postOut");
    SemanticObject semObj = SemanticObject.getSemanticObject(pOut);

    if(semObj.createGenericInstance() instanceof Photo)
    {
    Photo photo = (Photo) semObj.getGenericInstance();
    
    String pname = getPropertyName(semObj, Photo.social_hasPhoto.getName());

    Iterator<String> itPhotos = photo.listPhotos();

    SWBResourceURL url = paramRequest.getActionUrl();
    SWBResourceURL urlR = paramRequest.getRenderUrl();
    url.setParameter("postOut", request.getParameter("postOut"));

    PostOut postOut = null;
    postOut = (PostOut) semObj.createGenericInstance();
    
    if (itPhotos.hasNext()) {
        %>
        <div style="height: 450px">

            <table>
                <%

                    while (itPhotos.hasNext()) {
                        String sphoto = itPhotos.next();
                %>
                <tr>
                    <td><a href="#" class="eliminar" onclick="submitUrl('<%=url.setAction("deletePhoto").setParameter("idPhoto", sphoto).setParameter("postOut", pOut)%>',this); return false; ">Eliminar</a></td> 
                    <td><img src="<%=SWBPortal.getWebWorkPath()%><%=photo.getWorkPath()%>/<%=sphoto%>" width="300" height="300"> <p><%=sphoto%><p> </td>
                </tr>        
                <%
                    }
                %>
                <tr>
                    <td></td>
                    <td>           
                        <input type="button" onclick="submitUrl('<%=urlR.setMode("editWindow").setParameter("postOut", postOut.getURI()).setParameter("wsite", postOut.getSemanticObject().getModel().getName())%>',this); return false; " value="Cerrar" > 
                    </td>
                </tr>
            </table>
        </div>
        <%
        } else {
        %>

        <h1>Sin fotos asociadas. </h1>   
        <p>
            <input type="button" onclick="submitUrl('<%=urlR.setMode("editWindow").setParameter("postOut", postOut.getURI()).setParameter("wsite", postOut.getSemanticObject().getModel().getName())%>',this); return false; " value="Cerrar" > 
        </p>
    </div>
    <%
        }
    %>
    <p>
    </p>
    <%
    }else if(semObj.createGenericInstance() instanceof Message)
    {
        Message message = (Message) semObj.getGenericInstance();
        
        String pname = getPropertyName(semObj, Message.social_hasFile.getName());

        Iterator<String> itFiles = message.listFiles();

        SWBResourceURL url = paramRequest.getActionUrl();
        SWBResourceURL urlR = paramRequest.getRenderUrl();
        url.setParameter("postOut", request.getParameter("postOut"));

        PostOut postOut = null;
        postOut = (PostOut) semObj.createGenericInstance();

        if (itFiles.hasNext()) {
            %>
            <div style="height: 450px">

                <table>
                    <%

                        while (itFiles.hasNext()) {
                            String sFile = itFiles.next();
                            String sFileTmp=sFile;
                            if (sFileTmp.startsWith(pname)) {
                                sFileTmp = sFileTmp.substring(pname.length() + 1);
                            }
                    %>
                    <tr>
                        <td><a href="#" class="eliminar" onclick="submitUrl('<%=url.setAction("deletePhoto").setParameter("idPhoto", sFile).setParameter("postOut", pOut)%>',this); return false; ">Eliminar</a></td> 
                        <td><img src="<%=SWBPortal.getWebWorkPath()%><%=message.getWorkPath()%>/<%=sFile%>" width="300" height="300"> <p><%=sFileTmp%><p> </td>
                    </tr>        
                    <%
                        }
                    %>
                    <tr>
                        <td></td>
                        <td>           
                            <input type="button" onclick="submitUrl('<%=urlR.setMode("editWindow").setParameter("postOut", postOut.getURI()).setParameter("wsite", postOut.getSemanticObject().getModel().getName())%>',this); return false; " value="Cerrar" > 
                        </td>
                    </tr>
                </table>
            </div>
            <%
            } else {
            %>

            <h1>Sin documentos asociados. </h1>   
            <p>
                <input type="button" onclick="submitUrl('<%=urlR.setMode("editWindow").setParameter("postOut", postOut.getURI()).setParameter("wsite", postOut.getSemanticObject().getModel().getName())%>',this); return false; " value="Cerrar" > 
            </p>
        </div>
        <%
            }
        %>
        <p>
        </p>
    <%
     }
    %>
</body>
</html>

<%!
    private String getPropertyName(SemanticObject obj, String propName) 
    {
        return propName + "_" + (obj.getId()==null?"new":obj.getId());
    }
    
%>
