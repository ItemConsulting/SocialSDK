package com.ibm.sbt.services.client.connections.profiles;

import static com.ibm.sbt.services.client.connections.profiles.utils.ProfilesConstants.FORMAT;
import static com.ibm.sbt.services.client.connections.profiles.utils.ProfilesConstants.FULL;
import static com.ibm.sbt.services.client.connections.profiles.utils.ProfilesConstants.OUTPUT;
import static com.ibm.sbt.services.client.connections.profiles.utils.ProfilesConstants.VCARD;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ibm.commons.util.StringUtil;
import com.ibm.sbt.services.client.ClientService;
import com.ibm.sbt.services.client.ClientServicesException;
import com.ibm.sbt.services.client.connections.profiles.utils.Messages;
import com.ibm.sbt.services.endpoints.Endpoint;

/**
 * ProfileAdminService can be used to perform Admin operations related to Connection Profiles. 
 * 
 * @Represents Connections ProfileAdminService
 * @author Swati Singh
 * @author Carlos Manias
 * <pre>
 * Sample Usage
 * {@code
 *  ProfileAdminService _service = new ProfileAdminService();
 *  Profile profile = _service.getProfile("testUser61@renovations.com", false);
 *  profile.set("uid", "testUser");
 *  boolean success = service.createProfile(profile);
 * }
 * </pre>
 */
public class ProfileAdminService extends ProfileService {

	private static final long serialVersionUID = 3976235045185720867L;

	/**
	 * Constructor Creates ProfileAdminService Object with default endpoint and default cache size
	 */
	public ProfileAdminService() {
		this(DEFAULT_ENDPOINT_NAME, DEFAULT_CACHE_SIZE);
    }
	
	/**
	 * Constructor 
	 * 
	 * @param endpoint
	 *            Creates ProfileAdminService with specified endpoint and a default CacheSize
	 */
	public ProfileAdminService(Endpoint endpoint) {
		super(endpoint);
	}
	
	/**
	 * Constructor 
	 * 
	 * @param endpoint
	 *            Creates ProfileAdminService with specified endpoint and a default CacheSize
	 */
	public ProfileAdminService(String endpoint) {
		this(endpoint, DEFAULT_CACHE_SIZE);
	}
	
	/**
	 * Constructor 
	 * 
	 * @param endpoint
	 * @param cacheSize
	 *            Creates ProfileAdminService with specified endpoint and CacheSize
	 */
	public ProfileAdminService(String endpoint, int cacheSize) {
		super(endpoint, cacheSize);
	}
	
	/**
	 * Wrapper method to delete a User's profile
	 * <p>
	 * User should be logged in as a administrator to call this method
	 * 
	 * @param id
	 * 			unique identifier of user whose profile is to be deleted, it can either be a email or userid
	 * @throws ProfileServiceException 
	 */
	public void deleteProfile(String id) throws ProfileServiceException
	{	
		if (StringUtil.isEmpty(id)) {
			throw new ProfileServiceException(null, Messages.InvalidArgument_1);
		}
		try{
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(getIdParameter(id), id);
			String deleteUrl = ProfileUrls.ADMIN_PROFILE_ENTRY.format(this);
			super.deleteData(deleteUrl, parameters, getIdParameter(id));
		}
		catch(ClientServicesException e){
			throw new ProfileServiceException(e, Messages.DeleteProfileException, id);
		} catch (IOException e) {
			throw new ProfileServiceException(e, Messages.DeleteProfileException, id);
		}
	}

	/**
	 * Wrapper method to create a User's profile
	 * <p>
	 * User should be logged in as a administrator to call this method
	 * 
	 * @param Profile
	 * @throws ProfileServiceException 
	 */
	public void createProfile(Profile profile) throws ProfileServiceException {
		if (profile == null) {
			throw new ProfileServiceException(null, Messages.InvalidArgument_3);
		}		
		try {
			Map<String, String> parameters = new HashMap<String,String>();
			String id = profile.getUserid();
			parameters.put(getIdParameter(id), id);
			Object createPayload = constructCreateRequestBody(profile);
			
			String createUrl = ProfileUrls.ADMIN_PROFILES.format(this);
			super.createData(createUrl, parameters, createPayload, ClientService.FORMAT_CONNECTIONS_OUTPUT);
			
		}catch(ClientServicesException e) {
			throw new ProfileServiceException(e, Messages.CreateProfileException, profile.getUserid());
		} catch (IOException e) {
			throw new ProfileServiceException(e, Messages.CreateProfileException, profile.getUserid());
		}

	}

	/**
	 * 
	 * @param <b>parameters</b> a map of search keys<br/>
	 * <b>email</b>	Internet email address. Returns all profiles that contain a matching email address.<br/>
	 * <b>key</b>	An ID generated by Profiles to identify a person.<br/>
	 * <b>ps</b>	Page size. Specifies the number of entries to return per page. Use the rel="next" link to retrieve additional pages.<br/>
	 * <b>uid</b>	An organizationally specific unique identifier for the user<br/>
	 * <b>userid</b>	Unique ID that represents a specific person. <br/>
	 * @return a list or a list with matching profiles
	 * @throws ClientServicesException 
	 */
	@Override
	public ProfileList searchProfiles(Map<String,String> parameters) throws ProfileServiceException {
		
		String url = ProfileUrls.ADMIN_PROFILES.format(this);
		
		return getProfileEntityList(url, parameters);
	}

	/**
	 * Wrapper method to update a User's profile
	 * 
	 * @param Profile
	 * @throws ProfileServiceException
	 */
	@Override
	public void updateProfile(Profile profile) throws ProfileServiceException {

		if (profile == null) {
			throw new ProfileServiceException(null, Messages.InvalidArgument_3);
		}
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put(OUTPUT, VCARD);
			parameters.put(FORMAT, FULL);
			String id = profile.getUserid();
			parameters.put(getIdParameter(id), id);
			Object updateProfilePayload = constructUpdateRequestBody(profile);
			String updateUrl = ProfileUrls.ADMIN_PROFILE_ENTRY.format(this);
			super.updateData(updateUrl, parameters,updateProfilePayload, getIdParameter(profile.getAsString("uid")));
			profile.clearFieldsMap();
		} catch (ClientServicesException e) {
			throw new ProfileServiceException(e, Messages.UpdateProfileException);
		} catch (IOException e) {
			throw new ProfileServiceException(e, Messages.UpdateProfileException);
		}
	}
}
