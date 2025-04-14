import axios from "axios";

export async function searchPatientByName(name: string) {
  const response = await axios.get(
    `http://localhost:8080/pacientes/nome/${name}/indicesMaisRecentes`
  );
  return response.data;
}

export async function loadPatients() {
  const response = await axios.get(`http://localhost:8080/pacientes`);
  return response.data;
}
