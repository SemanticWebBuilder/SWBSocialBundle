package org.semanticwb.social.base;


   /**
   * Objeto que define un Sitio Web de Administración de SWBSocial 
   */
public abstract class SocialAdminBase extends org.semanticwb.model.AdminWebSite implements org.semanticwb.model.Countryable,org.semanticwb.model.Localeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.OntologyDepable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Undeleteable,org.semanticwb.model.Indexable,org.semanticwb.model.Trashable,org.semanticwb.model.FilterableNode
{
   /**
   * Clase que concentra propiedades para configuración general de facebook., para mostrar en sitio admin.
   */
    public static final org.semanticwb.platform.SemanticClass social_FacebookGC=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#FacebookGC");
   /**
   * Una unica instancia de FacebookGC.
   */
    public static final org.semanticwb.platform.SemanticProperty social_adm_facebookgc=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#adm_facebookgc");
   /**
   * Clase que concentra propiedades para configuración general de twitter., para mostrar en sitio admin.
   */
    public static final org.semanticwb.platform.SemanticClass social_TwitterGC=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TwitterGC");
   /**
   * Una unica instancia de twittergc
   */
    public static final org.semanticwb.platform.SemanticProperty social_adm_twittergc=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#adm_twittergc");
   /**
   * Clase que concentra propiedades para configuración general de youtube., para mostrar en sitio admin.
   */
    public static final org.semanticwb.platform.SemanticClass social_YoutubeGC=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#YoutubeGC");
   /**
   * Una unica instancia de YoutubeGC.
   */
    public static final org.semanticwb.platform.SemanticProperty social_adm_youtubegc=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#adm_youtubegc");
   /**
   * Es una pagina web utilizada para mostrar opciones del menu dentro de la administración de SWB
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_MenuItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#MenuItem");
   /**
   * Es una pagina web utilizada para mostrar comportamientos (tabs) dentro de la administración de SWB
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_ObjectBehavior=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");
   /**
   * Define una Collección de objetos de una clase especificada con la propiedad "collectionClass"
   */
    public static final org.semanticwb.platform.SemanticClass swb_Collection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Collection");
   /**
   * Objeto que define un Sitio Web de Administración de SWBSocial
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialAdmin=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialAdmin");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialAdmin");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialAdmin for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdmins(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialAdmin for all models
       * @return Iterator of org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdmins()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SocialAdmin
       * @param id Identifier for org.semanticwb.social.SocialAdmin
       * @return A org.semanticwb.social.SocialAdmin
       */
        public static org.semanticwb.social.SocialAdmin getSocialAdmin(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.social.SocialAdmin ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    org.semanticwb.model.GenericObject gobj=obj.createGenericInstance();
                    if(gobj instanceof org.semanticwb.social.SocialAdmin)
                    {
                        ret=(org.semanticwb.social.SocialAdmin)gobj;
                    }
                }
            }
            return ret;
        }
       /**
       * Create a org.semanticwb.social.SocialAdmin
       * @param id Identifier for org.semanticwb.social.SocialAdmin
       * @return A org.semanticwb.social.SocialAdmin
       */
        public static org.semanticwb.social.SocialAdmin createSocialAdmin(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.social.SocialAdmin)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialAdmin
       * @param id Identifier for org.semanticwb.social.SocialAdmin
       */
        public static void removeSocialAdmin(String id)
        {
            org.semanticwb.social.SocialAdmin obj=getSocialAdmin(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialAdmin
       * @param id Identifier for org.semanticwb.social.SocialAdmin
       * @return true if the org.semanticwb.social.SocialAdmin exists, false otherwise
       */

        public static boolean hasSocialAdmin(String id)
        {
            return (getSocialAdmin(id)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Adm_facebookgc
       * @param value Adm_facebookgc of the type org.semanticwb.social.FacebookGC
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByAdm_facebookgc(org.semanticwb.social.FacebookGC value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_adm_facebookgc, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Adm_facebookgc
       * @param value Adm_facebookgc of the type org.semanticwb.social.FacebookGC
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByAdm_facebookgc(org.semanticwb.social.FacebookGC value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_adm_facebookgc,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined SubModel
       * @param value SubModel of the type org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined DefaultTemplate
       * @param value DefaultTemplate of the type org.semanticwb.model.Template
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Ontology
       * @param value Ontology of the type org.semanticwb.model.Ontology
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined HomePage
       * @param value HomePage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined UserRepository
       * @param value UserRepository of the type org.semanticwb.model.UserRepository
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Adm_twittergc
       * @param value Adm_twittergc of the type org.semanticwb.social.TwitterGC
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByAdm_twittergc(org.semanticwb.social.TwitterGC value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_adm_twittergc, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Adm_twittergc
       * @param value Adm_twittergc of the type org.semanticwb.social.TwitterGC
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByAdm_twittergc(org.semanticwb.social.TwitterGC value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_adm_twittergc,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByModelProperty(org.semanticwb.model.ModelProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined ModelProperty
       * @param value ModelProperty of the type org.semanticwb.model.ModelProperty
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByModelProperty(org.semanticwb.model.ModelProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasModelProperty,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Adm_youtubegc
       * @param value Adm_youtubegc of the type org.semanticwb.social.YoutubeGC
       * @param model Model of the org.semanticwb.social.SocialAdmin
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByAdm_youtubegc(org.semanticwb.social.YoutubeGC value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_adm_youtubegc, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialAdmin with a determined Adm_youtubegc
       * @param value Adm_youtubegc of the type org.semanticwb.social.YoutubeGC
       * @return Iterator with all the org.semanticwb.social.SocialAdmin
       */

        public static java.util.Iterator<org.semanticwb.social.SocialAdmin> listSocialAdminByAdm_youtubegc(org.semanticwb.social.YoutubeGC value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialAdmin> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_adm_youtubegc,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialAdminBase.ClassMgr getSocialAdminClassMgr()
    {
        return new SocialAdminBase.ClassMgr();
    }

   /**
   * Constructs a SocialAdminBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialAdmin
   */
    public SocialAdminBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Adm_facebookgc
   * @param value Adm_facebookgc to set
   */

    public void setAdm_facebookgc(org.semanticwb.social.FacebookGC value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_adm_facebookgc, value.getSemanticObject());
        }else
        {
            removeAdm_facebookgc();
        }
    }
   /**
   * Remove the value for Adm_facebookgc property
   */

    public void removeAdm_facebookgc()
    {
        getSemanticObject().removeProperty(social_adm_facebookgc);
    }

   /**
   * Gets the Adm_facebookgc
   * @return a org.semanticwb.social.FacebookGC
   */
    public org.semanticwb.social.FacebookGC getAdm_facebookgc()
    {
         org.semanticwb.social.FacebookGC ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_adm_facebookgc);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.FacebookGC)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Adm_twittergc
   * @param value Adm_twittergc to set
   */

    public void setAdm_twittergc(org.semanticwb.social.TwitterGC value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_adm_twittergc, value.getSemanticObject());
        }else
        {
            removeAdm_twittergc();
        }
    }
   /**
   * Remove the value for Adm_twittergc property
   */

    public void removeAdm_twittergc()
    {
        getSemanticObject().removeProperty(social_adm_twittergc);
    }

   /**
   * Gets the Adm_twittergc
   * @return a org.semanticwb.social.TwitterGC
   */
    public org.semanticwb.social.TwitterGC getAdm_twittergc()
    {
         org.semanticwb.social.TwitterGC ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_adm_twittergc);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.TwitterGC)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Adm_youtubegc
   * @param value Adm_youtubegc to set
   */

    public void setAdm_youtubegc(org.semanticwb.social.YoutubeGC value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_adm_youtubegc, value.getSemanticObject());
        }else
        {
            removeAdm_youtubegc();
        }
    }
   /**
   * Remove the value for Adm_youtubegc property
   */

    public void removeAdm_youtubegc()
    {
        getSemanticObject().removeProperty(social_adm_youtubegc);
    }

   /**
   * Gets the Adm_youtubegc
   * @return a org.semanticwb.social.YoutubeGC
   */
    public org.semanticwb.social.YoutubeGC getAdm_youtubegc()
    {
         org.semanticwb.social.YoutubeGC ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_adm_youtubegc);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.YoutubeGC)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.MenuItem getMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.getMenuItem(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItems()
    {
        return org.semanticwb.model.MenuItem.ClassMgr.listMenuItems(this);
    }

    public org.semanticwb.model.MenuItem createMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.createMenuItem(id,this);
    }

    public void removeMenuItem(String id)
    {
        org.semanticwb.model.MenuItem.ClassMgr.removeMenuItem(id, this);
    }
    public boolean hasMenuItem(String id)
    {
        return org.semanticwb.model.MenuItem.ClassMgr.hasMenuItem(id, this);
    }

    public org.semanticwb.model.ObjectBehavior getObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.getObjectBehavior(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviors()
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.listObjectBehaviors(this);
    }

    public org.semanticwb.model.ObjectBehavior createObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.createObjectBehavior(id,this);
    }

    public void removeObjectBehavior(String id)
    {
        org.semanticwb.model.ObjectBehavior.ClassMgr.removeObjectBehavior(id, this);
    }
    public boolean hasObjectBehavior(String id)
    {
        return org.semanticwb.model.ObjectBehavior.ClassMgr.hasObjectBehavior(id, this);
    }

    public org.semanticwb.model.Collection getCollection(String id)
    {
        return org.semanticwb.model.Collection.ClassMgr.getCollection(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Collection> listCollections()
    {
        return org.semanticwb.model.Collection.ClassMgr.listCollections(this);
    }

    public org.semanticwb.model.Collection createCollection(String id)
    {
        return org.semanticwb.model.Collection.ClassMgr.createCollection(id,this);
    }

    public org.semanticwb.model.Collection createCollection()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Collection);
        return org.semanticwb.model.Collection.ClassMgr.createCollection(String.valueOf(id),this);
    } 

    public void removeCollection(String id)
    {
        org.semanticwb.model.Collection.ClassMgr.removeCollection(id, this);
    }
    public boolean hasCollection(String id)
    {
        return org.semanticwb.model.Collection.ClassMgr.hasCollection(id, this);
    }
}