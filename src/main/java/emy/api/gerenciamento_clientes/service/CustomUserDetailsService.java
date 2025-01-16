//package emy.api.gerenciamento_clientes.service;
//
//import emy.api.gerenciamento_clientes.entity.Cliente;
//import emy.api.gerenciamento_clientes.repository.ClienteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//
//@Service
//class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final ClienteRepository repository;
//
//    @Autowired
//    public UserDetailsServiceImpl(ClienteRepository userRepository) {
//        this.repository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Cliente user = repository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado com o email: " + email));
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getSenha(), new ArrayList<>());
//    }
//}
