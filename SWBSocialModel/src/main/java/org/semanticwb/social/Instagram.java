/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
package org.semanticwb.social;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.listener.Classifier;
import org.semanticwb.social.util.SWBSocialUtil;


   /**
   * Cuenta de Instagram 
   */
public class Instagram extends org.semanticwb.social.base.InstagramBase 
{
    private static Logger log = SWBUtils.getLogger(Instagram.class);
    public Instagram(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }


    @Override
    public void authenticate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String code = request.getParameter("code");
        //System.out.println("Entra al metodo authenticate ...codigo" + code);
        PrintWriter out = response.getWriter();
        String clientId = getAppKey();
        String clientSecret = getSecretKey();
        String uri = getRedirectUrl(request, paramRequest);        
        String uriTemp = "http://" + request.getServerName() + ":" + request.getServerPort() + SWBPortal.getWebWorkPath() + "/models/SWBAdmin/jsp/oauth/callbackInstagram.jsp";
        HttpSession session = request.getSession(true);
        
        if (code == null) {            
            //Se crea una variable de sesion para recuperar en el jsp la url dinamica
            
            session.setAttribute("redirectInstagram", uri);
            out.println("<script type=\"text/javascript\">");
            out.println("   location.href='"+ "https://api.instagram.com/oauth/authorize/?client_id=" + clientId + "&redirect_uri=" + uriTemp + "&response_type=code"+"'");
            //Permiso necesario para la actualización Junio 2016 hecha al API
 //           out.println("   location.href='"+ "https://api.instagram.com/oauth/authorize/?client_id=" + clientId + "&redirect_uri=" + uriTemp + "&response_type=code"+"&scope=public_content'");
            
            out.println("</script>");
        } else {
            Map<String, String> params = new HashMap<String, String>();
            params.put("code", code);
            params.put("client_id", clientId);
            params.put("client_secret", clientSecret);
            params.put("grant_type", "authorization_code");
            params.put("redirect_uri", uriTemp);

            try {
                String res = postRequest(params, "https://api.instagram.com/oauth/access_token", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
                //System.out.println("respuesta" + res);

                JSONObject userData = new JSONObject(res);
                String tokenAccess = userData.getString("access_token");
                JSONObject user = userData.getJSONObject("user");
                String userId = user.getString("id");
                setInstagramUserId(userId);
                setAccessToken(tokenAccess);
                setSn_authenticated(true);
                session.removeAttribute("redirectInstagram");                
                
                //System.out.println("token access:" + tokenAccess);
                //System.out.println("userId:" + userId);

            } catch (Exception ex) {
                log.error(ex);
            } finally {
                out.println("<script type=\"text/javascript\">");
                out.println("try{" +
                    "var form = window.opener.document.getElementById('authNet/"+ this.getEncodedURI() + "');\n"+
                    "if (form.onsubmit){"+
                        "var result = form.onsubmit.call(form);" +
                    "}" +
                    "if (result !== false){" +
                        "form.submit();" +
                    "}"+
                    "window.close();" +
                "}catch(e){window.opener=self; window.close();}");
                out.println("</script>");
            }
        }
    }
    
    private String getRedirectUrl(HttpServletRequest request, SWBParamRequest paramRequest) {
        //System.out.println("getRedirectUrl....");
        StringBuilder address = new StringBuilder(128);
        address.append("http://").append(request.getServerName()).append(":").append(request.getServerPort()).append("/").append(paramRequest.getUser().getLanguage()).append("/").append(paramRequest.getResourceBase().getWebSiteId()).append("/" + paramRequest.getWebPage().getId() + "/_rid/").append(paramRequest.getResourceBase().getId()).append("/_mod/").append(paramRequest.getMode()).append("/_lang/").append(paramRequest.getUser().getLanguage());
        //System.out.println("URL callback="+address);
        return address.toString();
    }
    
    CharSequence delimit(Collection<Map.Entry<String, String>> entries,
            String delimiter, String equals, boolean doEncode)
            throws UnsupportedEncodingException {

        if (entries == null || entries.isEmpty()) {
            return null;
        }
        StringBuilder buffer = new StringBuilder(64);
        boolean notFirst = false;
        for (Map.Entry<String, String> entry : entries) {
            if (notFirst) {
                buffer.append(delimiter);
            } else {
                notFirst = true;
            }
            CharSequence value = entry.getValue();
            buffer.append(entry.getKey());
            buffer.append(equals);
            buffer.append(doEncode ? encode(value) : value);
        }
        return buffer;
    }

    private String encode(CharSequence target) throws UnsupportedEncodingException {

        String result = "";
        if (target != null) {
            result = target.toString();
            result = URLEncoder.encode(result, "UTF8");
        }
        return result;
    }

    private static String getResponse(InputStream data) throws IOException {

        Reader in = new BufferedReader(new InputStreamReader(data, "UTF-8"));
        StringBuilder response = new StringBuilder(256);
        char[] buffer = new char[1000];
        int charsRead = 0;
        while (charsRead >= 0) {
            response.append(buffer, 0, charsRead);
            charsRead = in.read(buffer);
        }
        in.close();
        return response.toString();
    }

    private void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ex) {
            }
        }
    }

    private String postRequest(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {

        URL serverUrl = new URL(url);
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);

        HttpURLConnection conex = null;
        OutputStream out = null;
        InputStream in = null;
        String response = null;

        if (method == null) {
            method = "POST";
        }
        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod(method);
            conex.setDoOutput(true);
            conex.connect();
            out = conex.getOutputStream();
            out.write(paramString.toString().getBytes("UTF-8"));
            in = conex.getInputStream();
            response = getResponse(in);
        } catch (java.io.IOException ioe) {
            //ioe.printStackTrace();
            log.error("ERROR in postRequest:" + getResponse(conex.getErrorStream()), ioe);
        } finally {
            close(in);
            close(out);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }

    @Override
    public void listen(Stream stream){
        if(!isSn_authenticated() || getAccessToken() == null ){
            log.error("Not authenticated network: " + getTitle() + "!!!");
            return;
        }

        //System.out.println("Entra al metodo listen.... Instagram");
        ArrayList<ExternalPost> aListExternalPost = new ArrayList();
        String searchPhrases = formatsInstagramPhrases(stream);//getPhrases(stream.getPhrase());
        if(searchPhrases == null || searchPhrases.trim().isEmpty()){
            log.warn("\n Not a valid value to make a instagram search:" + searchPhrases);
            return;
        }

        if(searchPhrases == null || searchPhrases.isEmpty()){
            return;
        }

        SocialSite socialSite = (SocialSite)WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        
        int blockOfVideos = 500; //this is the default Value,
        try{
            if(socialSite.getBlockofMsgToClassify() > 0){
                blockOfVideos = socialSite.getBlockofMsgToClassify();
            }
        }catch(Exception e){}
        //System.out.println("Message Block Instagram:" + blockOfVideos);
        
        SocialNetStreamSearch socialStreamSerch = SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
        String lastPostId = null;
        if (socialStreamSerch != null) {
            lastPostId = getLastPostID(stream);
        }
        
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", this.getAccessToken());
        if(lastPostId != null || !lastPostId.trim().isEmpty()){
            params.put("min_tag_id", lastPostId);
        }
        
        boolean canGetMoreResults = true;
               
        int it = 0;
        int total = 0;
        try {
            do {
                String fbResponse = getRequest(params, "https://api.instagram.com/v1/tags/" + searchPhrases +"/media/recent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");                
                
                JSONObject respuesta = new JSONObject(fbResponse);                
                String next = null;
                String max_tag_id = null;
                
                
                JSONArray arr = respuesta.getJSONArray("data");
                //System.out.println("SIZE of data:" + arr.length());
                if(arr.length()==0){
                    canGetMoreResults = false;
                    break;
                }
                total += arr.length();
                ///System.out.println(" Current total:" + total);
                for(int ar = 0; ar < arr.length(); ar++){
                    String image = null;
                    String caption = null;
                    String username = null;
                    String userId = null;
                    String postId = null;
                    String video = null;
                    String postUrl = null;
                    String userProfileUrl = null;
                    Date created_time = null;
                    int likes = 0;
                    
                    
                    JSONObject tmp = arr.getJSONObject(ar);
                    String type = !tmp.isNull("type") ? tmp.getString("type") : "";
                    
                    if(!tmp.isNull("caption")){
                        if(!tmp.getJSONObject("caption").isNull("text")){
                            caption = tmp.getJSONObject("caption").getString("text");
                        }
                        if(!tmp.getJSONObject("caption").isNull("created_time")){
                            String created_tmp = tmp.getJSONObject("caption").getString("created_time");
                            created_time = new java.util.Date(Long.parseLong(created_tmp) * 1000);
                        }
                    }

                    if(!tmp.isNull("user")){
                        if(!tmp.getJSONObject("user").isNull("username")){
                            username = tmp.getJSONObject("user").getString("username");
                        }
                        if(!tmp.getJSONObject("user").isNull("id")){
                            userId = tmp.getJSONObject("user").getString("id");
                        }
                        if(!tmp.getJSONObject("user").isNull("profile_picture")){
                            userProfileUrl = tmp.getJSONObject("user").getString("profile_picture");
                        }
                    }

                    if(!tmp.isNull("id")){
                        postId = tmp.getString("id");
                    }
                    
                    if(!tmp.isNull("link")){
                        postUrl = tmp.getString("link");
                    }
                    
                    if(!tmp.isNull("likes")){
                        if(!tmp.getJSONObject("likes").isNull("count")){
                            likes = tmp.getJSONObject("likes").getInt("count");
                        }
                    }
                    if(type.equalsIgnoreCase("image")){
                        if(!tmp.isNull("images")){
                            if(!tmp.getJSONObject("images").isNull("thumbnail")){
                                image = tmp.getJSONObject("images").getJSONObject("thumbnail").getString("url");
                            }
                        }
                    }else if(type.equalsIgnoreCase("video")){
                        if(!tmp.isNull("videos")){
                            if(!tmp.getJSONObject("videos").isNull("low_resolution")){
                                if(!tmp.getJSONObject("videos").getJSONObject("low_resolution").isNull("url")){
                                    video = tmp.getJSONObject("videos").getJSONObject("low_resolution").getString("url");
                                }
                            }
                        }
                    }
                    
                    ExternalPost external = new ExternalPost();
                    external.setPostId(postId);
                    external.setCreatorId(userId);
                    external.setCreatorPhotoUrl(userProfileUrl);
                    external.setCreatorName(username);
                    external.setPostShared(likes);
                    external.setUserUrl("http://instagram.com/" + username);
                    external.setPostUrl(postUrl);
                    if(created_time !=null){
                    try{                        
                        if(created_time.after(new Date())){
                            external.setCreationTime(new Date());
                        }else{
                            external.setCreationTime(created_time);
                        }
                        }catch(Exception e){
                            log.error(e);
                        }
                    }else{
                        external.setCreationTime(new Date());
                    }
                    
                    external.setMessage(caption);                    
                    external.setSocialNetwork(this);

                    
                    if (image != null) {//Photo                        
                        ArrayList pictures = new ArrayList();
                        pictures.add(image);
                        external.setPictures(pictures);
                        external.setPostType(SWBSocialUtil.PHOTO);
                        
                    } else if (video != null) {//Video
                        external.setVideo(video);
                        external.setPostType(SWBSocialUtil.VIDEO);                        
                    }else{ continue;}
                    aListExternalPost.add(external);
                }
                //System.out.println("DATA SIZE:" + respuesta.getJSONArray("data").length());
                it++;                
                
                if(!respuesta.isNull("pagination")){
                    if(it==1){
                        //System.out.println("PAGINATION: " + respuesta.getJSONObject("pagination"));
                        if(!respuesta.getJSONObject("pagination").isNull("min_tag_id")){
                            String min_tag_id = respuesta.getJSONObject("pagination").getString("min_tag_id");
                            try{
                                setLastPostID(Long.parseLong(min_tag_id), stream);
                            }catch(NumberFormatException nfe){
                                log.error("Invalid ID value for NextDateToSearch:" , nfe);
                            }
                        }
                        //max_tag_id = respuesta.getJSONObject("pagination").getString("next_max_tag_id");                        
                        //System.out.println("the next Request:" + max_tag_id);
                        //params.put("max_tag_id", max_tag_id);
                    }
                    if(!respuesta.getJSONObject("pagination").isNull("next_url")){
                        next = respuesta.getJSONObject("pagination").getString("next_url");                        
                        //System.out.println("the next Request:" + next);
                    }
                    if(!respuesta.getJSONObject("pagination").isNull("next_max_tag_id")){
                        max_tag_id = respuesta.getJSONObject("pagination").getString("next_max_tag_id");                        
                        //System.out.println("the next Request:" + max_tag_id);
                        params.put("max_tag_id", max_tag_id);
                    }else{//EXIT
                        //it= 100;
                        canGetMoreResults = false;
                    }
                }else{
                    canGetMoreResults = false;
                }
            //} while (canGetMoreResults);
            } while (canGetMoreResults && total < 2000);
            //System.out.println("TOTAL READS;" + total);
            //Almacena los nuevos limites para las busquedas posteriores en Facebook

            
        } catch (JSONException jsone) {
            log.error("JSON al parsear datos recibidos ", jsone);
        } catch (IOException ioe) {
            log.error("IO, al recibir informacion ",  ioe);
        } catch (Exception e) {
            log.error("Exception, al ejecutar la búsqueda ", e);
        }

        //System.out.println("Total POSTS in Array: " + aListExternalPost.size());

        if (aListExternalPost.size() > 0) {
            new Classifier(aListExternalPost, stream, this, true);
        }
        //System.out.println("Total POSTS: " + total);
    }
    
    
    /**
     @Override
    public void listen(Stream stream){
        if(!isSn_authenticated() || getAccessToken() == null ){
            log.error("Not authenticated network: " + getTitle() + "!!!");
            return;
        }

        System.out.println("Entra al metodo listen.... Instagram");
        ArrayList<ExternalPost> aListExternalPost = new ArrayList();
        String searchPhrases = formatsInstagramPhrases(stream);//getPhrases(stream.getPhrase());
        if(searchPhrases == null || searchPhrases.isEmpty()){
            log.warn("\n Not a valid value to make a instagram search:" + searchPhrases);
            return;
        }

        if(searchPhrases == null || searchPhrases.isEmpty()){
            return;
        }

        SocialSite socialSite = (SocialSite)WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        
        int blockOfPosts = 500; //this is the default Value,
        try{
            if(socialSite.getBlockofMsgToClassify() > 0){
                blockOfPosts = socialSite.getBlockofMsgToClassify();
            }
        }catch(Exception e){}
        System.out.println("Message Block Instagram:" + blockOfPosts);
        
        SocialNetStreamSearch socialStreamSerch = SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
        String lastPostId = null;
        Long lastPostIdLong = 0L;
        if(socialStreamSerch != null){
            lastPostId = getLastPostID(stream);
            if(lastPostId != null || !lastPostId.trim().isEmpty()){
                try{
                    lastPostIdLong = Long.parseLong(lastPostId);
                }catch(NumberFormatException nfe){}            
            }
        }
        
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", this.getAccessToken());
        
        
        boolean canGetMoreResults = true;
               
        int it = 0;
        int total = 0;
        try {
            do {
                String fbResponse = getRequest(params, "https://api.instagram.com/v1/tags/" + searchPhrases +"/media/recent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");                
                
                JSONObject respuesta = new JSONObject(fbResponse);                
                Long firstElementObtained = 0L;
                String max_tag_id = null;
                
                
                JSONArray arr = respuesta.getJSONArray("data");
                System.out.println("SIZE of data:" + arr.length());
                if(arr.length()==0){
                    canGetMoreResults = false;
                    break;
                }
                total += arr.length();
                System.out.println(" Current total:" + total);
                for(int ar = 0; ar < arr.length(); ar++){
                    String image = null;
                    String caption = null;
                    String username = null;
                    String userId = null;
                    String postId = null;
                    Long postIdLong = 0L;
                    String video = null;
                    String postUrl = null;
                    String userProfileUrl = null;
                    Date created_time = null;
                    
                    
                    JSONObject tmp = arr.getJSONObject(ar);
                    String type = !tmp.isNull("type") ? tmp.getString("type") : "";
                    
                    if(!tmp.isNull("id")){
                        postId = tmp.getString("id");
                        if(postId.contains("_")){
                            postId = postId.split("_")[0];
                            System.out.println("the id::" + postId);
                            try{
                                postIdLong = Long.parseLong(postId);
                            }catch(NumberFormatException nfe){}
                        }
                    }
                    if(ar == 0){
                        firstElementObtained = postIdLong;
                    }
                    
                    if(postIdLong <= lastPostIdLong){//If current post is smaller than the saved then has reached the limit
                        canGetMoreResults = false;
                        break;
                    }
                    
                    if(!tmp.isNull("caption")){
                        if(!tmp.getJSONObject("caption").isNull("text")){
                            caption = tmp.getJSONObject("caption").getString("text");
                        }
                        if(!tmp.getJSONObject("caption").isNull("created_time")){
                            String created_tmp = tmp.getJSONObject("caption").getString("created_time");
                            created_time = new java.util.Date(Long.parseLong(created_tmp) * 1000);
                        }
                    }

                    if(!tmp.isNull("user")){
                        if(!tmp.getJSONObject("user").isNull("username")){
                            username = tmp.getJSONObject("user").getString("username");
                        }
                        if(!tmp.getJSONObject("user").isNull("id")){
                            userId = tmp.getJSONObject("user").getString("id");
                        }
                        if(!tmp.getJSONObject("user").isNull("profile_picture")){
                            userProfileUrl = tmp.getJSONObject("user").getString("profile_picture");
                        }
                    }                    
                    
                    
                    
                    if(!tmp.isNull("link")){
                        postUrl = tmp.getString("link");
                    }
                    
                    if(type.equalsIgnoreCase("image")){
                        if(!tmp.isNull("images")){
                            if(!tmp.getJSONObject("images").isNull("thumbnail")){
                                image = tmp.getJSONObject("images").getJSONObject("thumbnail").getString("url");
                            }
                        }
                    }else if(type.equalsIgnoreCase("video")){
                        if(!tmp.isNull("videos")){
                            if(!tmp.getJSONObject("videos").isNull("low_resolution")){
                                if(!tmp.getJSONObject("videos").getJSONObject("low_resolution").isNull("url")){
                                    video = tmp.getJSONObject("videos").getJSONObject("low_resolution").getString("url");
                                }
                            }
                        }
                    }
                    
                    ExternalPost external = new ExternalPost();
                    external.setPostId(postId);
                    external.setCreatorId(userId);
                    external.setCreatorPhotoUrl(userProfileUrl);
                    external.setCreatorName(username);

                    external.setUserUrl("http://instagram.com/" + username);
                    external.setPostUrl(postUrl);
                    if(created_time !=null){
                    try{                        
                        if(created_time.after(new Date())){
                            external.setCreationTime(new Date());
                        }else{
                            external.setCreationTime(created_time);
                        }
                        }catch(Exception e){
                            System.out.println("error");
                            e.printStackTrace();
                        }
                    }else{
                        external.setCreationTime(new Date());
                    }
                    
                    external.setMessage(caption);                    
                    external.setSocialNetwork(this);

                    
                    if (image != null) {//Photo                        
                        ArrayList pictures = new ArrayList();
                        pictures.add(image);
                        external.setPictures(pictures);
                        external.setPostType(SWBSocialUtil.PHOTO);
                        
                    } else if (video != null) {//Video
                        external.setVideo(video);
                        external.setPostType(SWBSocialUtil.VIDEO);                        
                    }else{ continue;}
                    aListExternalPost.add(external);
                }
                System.out.println("DATA SIZE:" + respuesta.getJSONArray("data").length());
                it++;                
                
                if(!respuesta.isNull("pagination")){
                    if(it == 1){//only for the first iteration and the first element of the 'data' object
                        try{
                            setLastPostID(firstElementObtained, stream);
                        }catch(NumberFormatException nfe){
                            log.error("Invalid ID value for NextDateToSearch:" , nfe);
                        }
                    
                        
                        //max_tag_id = respuesta.getJSONObject("pagination").getString("next_max_tag_id");                        
                        //System.out.println("the next Request:" + max_tag_id);
                        //params.put("max_tag_id", max_tag_id);
                    }
                   
                    if(!respuesta.getJSONObject("pagination").isNull("next_max_tag_id")){
                        max_tag_id = respuesta.getJSONObject("pagination").getString("next_max_tag_id");                        
                        System.out.println("the next Request:" + max_tag_id);
                        params.put("max_tag_id", max_tag_id);
                    }else{//EXIT
                        //it= 100;
                        canGetMoreResults = false;
                    }
                }else{
                    canGetMoreResults = false;
                }
            //} while (canGetMoreResults);
            } while (canGetMoreResults && total < 100);
            System.out.println("TOTAL READS;" + total);
            //Almacena los nuevos limites para las busquedas posteriores en Facebook

            
        } catch (JSONException jsone) {
            log.error("JSON al parsear datos recibidos ", jsone);
        } catch (IOException ioe) {
            log.error("IO, al recibir informacion ",  ioe);
        } catch (Exception e) {
            log.error("Exception, al ejecutar la búsqueda ", e);
        }

        System.out.println("Total POSTS in Array: " + aListExternalPost.size());

        if (aListExternalPost.size() > 0) {
            new Classifier(aListExternalPost, stream, this, true);
        }
        System.out.println("Total POSTS: " + total);
    }
     */
    private String formatsInstagramPhrases(Stream stream){       
        String exactPhrases = "";
        if(stream.getPhrase() != null && !stream.getPhrase().trim().isEmpty()){//Exact phrase
            exactPhrases = stream.getPhrase();
            exactPhrases = SWBSocialUtil.Strings.replaceSpecialCharacters(exactPhrases);
            exactPhrases = exactPhrases.split(" ")[0];
        }
        
        //System.out.println("Instagram Final String-->" + exactPhrases + "<-");        
        return exactPhrases;
    }
    
    private String getRequest(Map<String, String> params, String url,
            String userAgent) throws IOException {

        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url + "?" + paramString);

        HttpURLConnection conex = null;
        InputStream in = null;
        String response = null;

        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod("GET");
            conex.setDoOutput(true);
            conex.connect();
            in = conex.getInputStream();
            response = getResponse(in);

        } catch (java.io.IOException ioe) {
            response = getResponse(conex.getErrorStream());
            ioe.printStackTrace();
        } finally {
            close(in);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }

    /**
     *
     * @param id identifier of the facebook user
     * @return returns a JSONObject containing the requested fields with a valid
     * value {friends, followers, latitude, longitude, country_code, place_name}
     * all the values might or might not be present in the JSONObject.
     */
    @Override
    public JSONObject getUserInfobyId(String userId) {
        HashMap<String, String> params = new HashMap<String, String>(2);        
        params.put("access_token", this.getAccessToken());

        JSONObject userInfo = new JSONObject();
        try {
            String insResponse = getRequest(params, "https://api.instagram.com/v1/users/" + userId,
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            try {
                JSONObject parseUsrInf = new JSONObject(insResponse);
                JSONObject user = null;

                //System.out.println("THE RESPONSE:" + parseUsrInf);
                if (parseUsrInf.isNull("data")) {
                    log.error("Not data found for id:" + userId);
                    return null;
                }
                
                user = parseUsrInf.getJSONObject("data");

                if(!user.isNull("counts")){
                    //Check if fields exists and if they're not null
                    if(!user.getJSONObject("counts").isNull("follows")){
                        userInfo.put("friends", user.getJSONObject("counts").getInt("follows"));
                    }else {
                        userInfo.put("friends", 0);
                    }
                    if(!user.getJSONObject("counts").isNull("followed_by")){
                        userInfo.put("followers", user.getJSONObject("counts").getInt("followed_by"));
                    }else {
                        userInfo.put("followers", 0);
                    }
                }
            } catch (JSONException jsone) {
                log.error("Error parsing json response from facebook ", jsone);
            }

        } catch (IOException e) {
            log.error("Error getting user information ", e);
        }
        return userInfo;
    }
    
    private String getLastPostID(Stream stream) {
        String lastPostID = null;
        //System.out.println("entrando al metodo get Last Post....");
        SocialNetStreamSearch socialStreamSerch = SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
        //System.out.append("NDTS:" + socialStreamSerch.getNextDatetoSearch());
        
        try {
            if (socialStreamSerch != null && socialStreamSerch.getNextDatetoSearch() != null) {
                lastPostID = socialStreamSerch.getNextDatetoSearch();
                //System.out.println("RECOVERING NEXTDATETOSEARCH: " + socialStreamSerch.getNextDatetoSearch());
            } else {
                lastPostID = "";
            }
        } catch (Exception nfe) {
            lastPostID = "";
            log.error("Error in getLastPostID():" + nfe);
            //System.out.println("Invalid value found in NextDatetoSearch(). Set:" + lastPostID);
        }
        
        return lastPostID;
    }
    
    private void setLastPostID(Long recentPostId, Stream stream) {
        //System.out.println("entrando al metodo setLastPostID....");
        
        try {
            Long storedValue = 0L;
            SocialNetStreamSearch socialStreamSerch = SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
            if (socialStreamSerch != null && socialStreamSerch.getNextDatetoSearch() != null) {
                storedValue = Long.parseLong(socialStreamSerch.getNextDatetoSearch());
            }
            
            //System.out.println("stored Value : " + storedValue + "  dateVideo:  " + recentPostId);
             
            if (recentPostId > storedValue) {
                socialStreamSerch.setNextDatetoSearch(String.valueOf(recentPostId));
            }/* else {
                System.out.println("NO ESTÁ GUARDANDO NADA PORQUE EL VALOR ALMACENADO YA ES IGUAL O MAYOR AL ACTUAL");
            }*/
        } catch (NumberFormatException nfe) {
            log.error("Error in setLastVideoID():" + nfe);
        }
    }
    
    
    @Override
    public double getUserKlout(String instagramUserID)
    {
        String url_1="http://api.klout.com/v2/identity.json/ig/"+instagramUserID;
        String kloutJsonResponse_1=getData(url_1);
        //System.out.println("kloutResult step-1:"+kloutJsonResponse_1);
        
        //Obtener id de json
        try
        {
            if(kloutJsonResponse_1!=null)
            {
                JSONObject userData = new JSONObject(kloutJsonResponse_1);
                String kloutUserId = userData != null && userData.get("id") != null ? (String) userData.get("id") : "";
                //System.out.println("kloutId de Resultado en Json:"+kloutUserId);
            
                //Segunda llamada a la red social Klout, para obtener Json de Score del usuario (kloutUserId) encontrado
                if(kloutUserId!=null)
                {
                    String url_2="http://api.klout.com/v2/user.json/"+kloutUserId+"/score";
                    String kloutJsonResponse_2=getData(url_2);
                    //System.out.println("kloutResult step-2-Json:"+kloutJsonResponse_2);

                    if(kloutJsonResponse_2!=null)
                    {
                         JSONObject userScoreData = new JSONObject(kloutJsonResponse_2);
                         Double kloutUserScore = userScoreData != null && userScoreData.get("score") != null ? (Double) userScoreData.get("score") : 0.00;
                         return Math.rint(kloutUserScore.doubleValue());
                    }
                    /*
                    //TEST: OBTENCIÓN DE TOPICS DEL USUARIO
                    String url_3="http://api.klout.com/v2/user.json/"+kloutUserId+"/topics";
                    System.out.println("url_3:"+url_3);
                    String kloutJsonResponse_3=getData(url_3);
                    System.out.println("kloutJsonResponse_3-Json:"+kloutJsonResponse_3);
                    if(kloutJsonResponse_3!=null)
                    {
                        JSONObject userScoreData = new JSONObject(kloutJsonResponse_3);
                        
                        JSONArray jArrayIds=userScoreData.getJSONArray("id");
                        for (int i = 0; i < jArrayIds.length(); i++) {
                            JSONObject jsonObj = jArrayIds.getJSONObject(i);
                            System.out.println("jsonObj:"+jsonObj);
                        }
                    }
                       
                    String kloutTopicId = userScoreData != null && userScoreData.get("id") != null ? (String)userScoreData.get("id"):"";
                    String kloutTopicDisplayName  = userScoreData != null && userScoreData.get("displayName") != null ? (String)userScoreData.get("displayName"):"";
                    String kloutTopicName  = userScoreData != null && userScoreData.get("name") != null ? (String)userScoreData.get("name"):"";
                    String kloutTopicSlug  = userScoreData != null && userScoreData.get("slug") != null ? (String)userScoreData.get("slug"):"";
                    String kloutTopicImgUrl  = userScoreData != null && userScoreData.get("imageUrl") != null ? (String)userScoreData.get("imageUrl"):"";
                    String kloutTopicType  = userScoreData != null && userScoreData.get("topicType") != null ? (String)userScoreData.get("topicType"):"";
                    System.out.println("kloutTopicType:"+kloutTopicType);
                    if(kloutTopicType.equals("sub"))
                    {
                       try{ 
                           SWBUtils.IO.log2File("c://kloutTypes.txt", kloutTopicType);
                       }catch(Exception e){log.error(e);
                       }
                       System.out.println("kloutUserId:"+kloutUserId);
                       System.out.println("kloutTopicId:"+kloutTopicId);
                       System.out.println("kloutTopicDisplayName:"+kloutTopicDisplayName);
                       System.out.println("kloutTopicName:"+kloutTopicName);
                       System.out.println("kloutTopicSlug:"+kloutTopicSlug);
                       System.out.println("kloutTopicImgUrl:"+kloutTopicImgUrl);
                    }
                    */
                    //FINALIZA TEST
                   
                }
            }
        }catch(JSONException je)
        {
            log.error(je);
        }
        return 0;
    }
    
    private static String getData(String url)
    {
        String answer = null;
        //String key=SWBContext.getAdminWebSite().getProperty("kloutKey");    //TODO:Ver con Jei x que no funciona esto...
        String key=SWBSocialUtil.getEnv("swbsocial/kloutKey", "8fkzgz7ngf7bth3nk94gnxkd");
        //System.out.println("key para KLOUT--Gg:"+key);
        if(key!=null)
        {
            url=url+"?key="+key;
            URLConnection conex = null;
            try {
                //System.out.println("Url a enviar a Klout:"+url);
                URL pagina = new URL(url);

                String host = pagina.getHost();
                //Se realiza la peticion a la página externa
                conex = pagina.openConnection();
                /*
                if (userAgent != null) {
                    conex.setRequestProperty("user-agent", userAgent);
                }*/
                if (host != null) {
                    conex.setRequestProperty("host", host);
                }
                conex.setDoOutput(true);

                conex.setConnectTimeout(20000); //15 segundos maximo, si no contesta la red Klout, cortamos la conexión
            } catch (Exception nexc) {
                conex = null;
            }
            //System.out.println("Twitter Klout/conex:"+conex);
            //Analizar la respuesta a la peticion y obtener el access token
            if (conex != null) {
                try
                {
                    //System.out.println("Va a checar esto en Klit:"+conex.getInputStream());
                    answer = getResponse(conex.getInputStream());
                }catch(Exception e)
                {
                    //log.error(e);
                }
                //System.out.println("Twitter Klout/answer-1:"+answer);
            }
        }
        //System.out.println("Twitter Klout/answer-2:"+answer);
        return answer;
    }
    
}
