import { useLocation, useNavigate } from "react-router-dom";
import {
  Activity,
  Heart,
  Home,
  Mail,
  User,
  Users,
  ArrowLeft,
} from "lucide-react";

export default function LatestIndexesPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const { patient } = location.state;

  const latestPulmonaryIndex =
    patient.listaIndicesPulmonares.length > 0
      ? patient.listaIndicesPulmonares.reduce((latest: any, current: any) =>
          new Date(current.dataColeta) > new Date(latest.dataColeta)
            ? current
            : latest
        )
      : null;

  const latestCardiacIndex =
    patient.listaIndicesCardiacos.length > 0
      ? patient.listaIndicesCardiacos.reduce((latest: any, current: any) =>
          new Date(current.dataColeta) > new Date(latest.dataColeta)
            ? current
            : latest
        )
      : null;

  const formatDate = (dateString: any) => {
    const date = new Date(dateString);
    return date.toLocaleDateString("pt-BR", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-green-400 to-green-600 p-4 md:p-8">
      <button
        onClick={() => navigate(-1)}
        className="p-2 rounded hover:bg-gray-100"
      >
        <ArrowLeft className="h-6 w-6" />
      </button>
      <div className="max-w-5xl mx-auto">
        <div className="bg-white rounded-t-xl shadow-lg p-6 mb-1">
          <div className="flex flex-col md:flex-row md:items-center justify-between">
            <div className="flex items-center gap-4">
              <div className="bg-green-100 p-3 rounded-full">
                <User className="h-8 w-8 text-green-600" />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-800">
                  {patient.nome}
                </h1>
                <div className="flex items-center gap-2 text-gray-500">
                  <span>{patient.idade} anos</span>
                  <span>•</span>
                  <span>{patient.sexo}</span>
                  <span>•</span>
                  <span className="bg-green-50 text-green-700 px-2 py-1 rounded-full text-xs font-medium">
                    {patient.tipo_sanguineo}
                  </span>
                </div>
              </div>
            </div>
            <div className="mt-4 md:mt-0">
              <span className="bg-green-600 text-white px-3 py-1 rounded-full text-sm">
                ID: {patient.id}
              </span>
            </div>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
          <div className="bg-white rounded-xl shadow-lg overflow-hidden">
            <div className="bg-green-50 p-4 border-b border-green-100">
              <h2 className="flex items-center gap-2 text-green-700 font-bold">
                <Activity className="h-5 w-5" />
                Índice Pulmonar Mais Recente
              </h2>
            </div>
            <div className="p-4">
              {latestPulmonaryIndex ? (
                <div className="space-y-2">
                  <div className="flex justify-between items-center">
                    <span className="text-gray-500">Valor:</span>
                    <span className="text-2xl font-bold text-green-600">
                      {Number.parseFloat(latestPulmonaryIndex.ind_pulm).toFixed(
                        4
                      )}
                    </span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span className="text-gray-500">Data da coleta:</span>
                    <span className="text-sm">
                      {formatDate(latestPulmonaryIndex.dataColeta)}
                    </span>
                  </div>
                </div>
              ) : (
                <p className="text-gray-500">
                  Nenhum índice pulmonar registrado
                </p>
              )}
            </div>
          </div>

          <div className="bg-white rounded-xl shadow-lg overflow-hidden">
            <div className="bg-green-50 p-4 border-b border-green-100">
              <h2 className="flex items-center gap-2 text-green-700 font-bold">
                <Heart className="h-5 w-5" />
                Índice Cardíaco Mais Recente
              </h2>
            </div>
            <div className="p-4">
              {latestCardiacIndex ? (
                <div className="space-y-2">
                  <div className="flex justify-between items-center">
                    <span className="text-gray-500">Valor:</span>
                    <span className="text-2xl font-bold text-green-600">
                      {Number.parseFloat(latestCardiacIndex.ind_card).toFixed(
                        4
                      )}
                    </span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span className="text-gray-500">Data da coleta:</span>
                    <span className="text-sm">
                      {formatDate(latestCardiacIndex.dataColeta)}
                    </span>
                  </div>
                </div>
              ) : (
                <p className="text-gray-500">
                  Nenhum índice cardíaco registrado
                </p>
              )}
            </div>
          </div>
        </div>

        <div className="bg-white rounded-b-xl shadow-lg p-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div>
              <h2 className="text-xl font-semibold text-gray-800 mb-4 flex items-center gap-2">
                <Users className="h-5 w-5 text-green-600" />
                Dados Pessoais
              </h2>
              <div className="space-y-3">
                <p>
                  <span className="text-gray-500">CPF:</span> {patient.cpf}
                </p>
                <p>
                  <span className="text-gray-500">RG:</span> {patient.rg}
                </p>
                <p>
                  <span className="text-gray-500">Data de Nasc.:</span>{" "}
                  {patient.data_nasc}
                </p>
                <p>
                  <span className="text-gray-500">Signo:</span> {patient.signo}
                </p>
                <p>
                  <span className="text-gray-500">Mãe:</span> {patient.mae}
                </p>
                <p>
                  <span className="text-gray-500">Pai:</span> {patient.pai}
                </p>
              </div>

              <div className="h-px bg-gray-200 my-4"></div>

              <h2 className="text-xl font-semibold text-gray-800 mb-4 flex items-center gap-2">
                <Mail className="h-5 w-5 text-green-600" />
                Contato
              </h2>
              <div className="space-y-3">
                <p>
                  <span className="text-gray-500">Email:</span> {patient.email}
                </p>
                <p>
                  <span className="text-gray-500">Tel. Fixo:</span>{" "}
                  {patient.telefone_fixo}
                </p>
                <p>
                  <span className="text-gray-500">Celular:</span>{" "}
                  {patient.celular}
                </p>
              </div>
            </div>

            <div>
              <h2 className="text-xl font-semibold text-gray-800 mb-4 flex items-center gap-2">
                <Home className="h-5 w-5 text-green-600" />
                Endereço
              </h2>
              <div className="space-y-3">
                <p>
                  <span className="text-gray-500">CEP:</span> {patient.cep}
                </p>
                <p>
                  <span className="text-gray-500">Endereço:</span>{" "}
                  {patient.endereco}, {patient.numero}
                </p>
                <p>
                  <span className="text-gray-500">Bairro:</span>{" "}
                  {patient.bairro}
                </p>
                <p>
                  <span className="text-gray-500">Cidade/UF:</span>{" "}
                  {patient.cidade} - {patient.estado}
                </p>
              </div>

              <div className="h-px bg-gray-200 my-4"></div>

              <h2 className="text-xl font-semibold text-gray-800 mb-4 flex items-center gap-2">
                <Activity className="h-5 w-5 text-green-600" />
                Dados Médicos
              </h2>
              <div className="space-y-3">
                <p>
                  <span className="text-gray-500">Altura:</span>{" "}
                  {patient.altura} m
                </p>
                <p>
                  <span className="text-gray-500">Peso:</span> {patient.peso} kg
                </p>
                <p>
                  <span className="text-gray-500">Tipo Sanguíneo:</span>{" "}
                  {patient.tipo_sanguineo}
                </p>
                <p>
                  <span className="text-gray-500">Cor Favorita:</span>{" "}
                  <span className="inline-flex items-center gap-2">
                    <span
                      className="inline-block w-4 h-4 rounded-full"
                      style={{ backgroundColor: patient.cor }}
                    ></span>
                    {patient.cor}
                  </span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
