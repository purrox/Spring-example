Properties env = new Properties();
	        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
	        
	        if(!providerURL.isEmpty()) {
	        	env.setProperty(Context.PROVIDER_URL, providerURL);
	        }
	        
	        if((!securityPrincipal.isEmpty()) && (securityCredentials.isEmpty())) {
	        	env.setProperty(Context.SECURITY_PRINCIPAL, securityPrincipal);
	        	env.setProperty(Context.SECURITY_CREDENTIALS, securityCredentials);
	        }
	        
	        JndiTemplate jndi = new JndiTemplate(env);
	        
	        try {
	        	dataSource = (DataSource) jndi.lookup(datasourceName);
	        } catch (NamingException e) {
	        	e.printStackTrace();
	        }
