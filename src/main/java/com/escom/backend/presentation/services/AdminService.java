package com.escom.backend.presentation.services;

import com.escom.backend.domain.dto.FarmaceuticoDTO;
import com.escom.backend.domain.dto.MedicoDTO;
import com.escom.backend.domain.dto.PacienteDTO;
import com.escom.backend.domain.entities.*;
import com.escom.backend.domain.repositories.*;
import com.escom.backend.utils.MatriculaUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private FarmaceuticoRepository farmaceuticoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MatriculaUtil matriculaUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void registrarMedico(MedicoDTO dto) {
        if (usuarioRepository.findByEmail(dto.email).isPresent()) {
            throw new RuntimeException("Email ya registrado.");
        }

        Usuario user = new Usuario();
        user.setEmail(dto.email);
        user.setPassword(encoder.encode(dto.password));
        user.setNombre(dto.nombre);
        user.setFechaNacimiento(dto.fechaNacimiento);
        user.setRol(Rol.MEDICO);

        Usuario savedUser = (Usuario) usuarioRepository.save(user);

        Medico medico = new Medico();
        medico.setUsuario(savedUser);
        medico.setEspecialidad(dto.especialidad);
        medico.setClinica(dto.clinica);
        medico.setCedula(dto.cedula);
        medico.setTelefono(dto.tel);

        medicoRepository.save(medico);
    }

    public void registrarFarmaceutico(FarmaceuticoDTO dto) {
        if (usuarioRepository.findByEmail(dto.email).isPresent()) {
            throw new RuntimeException("Email ya registrado.");
        }

        Usuario user = new Usuario();
        user.setEmail(dto.email);
        user.setPassword(encoder.encode(dto.password));
        user.setNombre(dto.nombre);
        user.setFechaNacimiento(dto.fechaNacimiento);
        user.setRol(Rol.FARMACEUTICO);

        Usuario savedUser = (Usuario) usuarioRepository.save(user);

        Farmaceutico farmaceutico = new Farmaceutico();
        farmaceutico.setUsuario(savedUser);
        farmaceutico.setFarmacia(dto.farmacia);
        farmaceutico.setTelefono(dto.tel);

        farmaceuticoRepository.save(farmaceutico);
    }

    public void registrarPaciente(PacienteDTO dto) {
        if (usuarioRepository.findByEmail(dto.email).isPresent()) {
            throw new RuntimeException("Email ya está registrado.");
        }

        Usuario user = new Usuario();
        user.setEmail(dto.email);
        user.setPassword(encoder.encode(dto.password));
        user.setNombre(dto.nombre);
        user.setFechaNacimiento(dto.fechaNacimiento);
        user.setRol(Rol.PACIENTE);
        
        Usuario savedUser = (Usuario) usuarioRepository.save(user);
        Paciente paciente = new Paciente();
        paciente.setUsuario(savedUser);
        paciente.setCurp(dto.curp);

        String matricula = matriculaUtil.generarMatricula(pacienteRepository);
        paciente.setMatricula(matricula);

        pacienteRepository.save(paciente);
    }
}
