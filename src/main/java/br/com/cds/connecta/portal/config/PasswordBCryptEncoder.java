package br.com.cds.connecta.portal.config;

/**
 * Responsible to encode all passwords that is not in accord with Bcrypt
 * structure.
 * 
 * @author cleto
 *
 */
//@Component
//public class PasswordBCryptEncoder implements ApplicationListener<ContextRefreshedEvent> {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
//
//	private Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
//
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent event) {
//
//		List<User> usuarios = userRepository.findAll();
//
//		for (User user : usuarios) {
//			if (user.getPassword() != null) {
//				if (!BCRYPT_PATTERN.matcher(user.getPassword()).matches()) {
//					user.setPassword(passwordEncoder.encode(user.getPassword()));
//					userRepository.save(user);
//				}
//			}
//		}
//
//	}
//}