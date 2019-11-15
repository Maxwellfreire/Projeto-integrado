package br.com.integrador.adm.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.integrador.adm.Activitys.CadastroFActivity;
import br.com.integrador.adm.Activitys.CadastroTActivity;
import br.com.integrador.adm.R;
import br.com.integrador.adm.model.Cargo;
import br.com.integrador.adm.model.Funcionario;
import br.com.integrador.adm.model.Time;


/**
 * Created by bruno on 07/04/18.
 */

public class APIAdapterFuncionario extends BaseAdapter {


    Context context;
    List<Funcionario> colecao;
    private Time Items;

    public APIAdapterFuncionario(final Context applicationContext,
                                 final List<Funcionario> colecao) {
        this.context = applicationContext;
        this.colecao = colecao;

    }

    @Override
    public int getCount() {
        return this.colecao != null ? this.colecao.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return this.colecao.get(i);
    }

    private Funcionario parsetItem(int i) {
        return this.colecao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Funcionario Item = (Funcionario) getItem(i);


        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.item_funcionario,
                            viewGroup, false);
        }

        // pega o objeto corrente da lista
        Funcionario funcionario = parsetItem(i);


        TextView campoID, campoNOME, campoCPF, campoSEXO, campoEMAIL, campoTELEFONE, campoCELULAR, campoNasc, CampoIDCARGOF;

        view = LayoutInflater.from(context).inflate(R.layout.item_funcionario, null);

        campoID = view.findViewById(R.id.getIDFuncionario);
        campoNOME = view.findViewById(R.id.getNomeFuncionario);
        campoCPF = view.findViewById(R.id.getCPFFuncionario);
        campoSEXO = view.findViewById(R.id.getSexoFuncionario);
        campoEMAIL = view.findViewById(R.id.getEmailFuncionario);
        campoTELEFONE = view.findViewById(R.id.getTeleFuncionario);
        campoCELULAR = view.findViewById(R.id.getCelularFuncionario);
        campoNasc = view.findViewById(R.id.getNascFuncionario);
        CampoIDCARGOF = view.findViewById(R.id.getIDCargoFuncionario);

        campoID.setText(Integer.toString(funcionario.getMatricula()));
        campoNOME.setText(funcionario.getNameFuncionario());
        campoCPF.setText(funcionario.getCpf());
        campoSEXO.setText(funcionario.getSexo());
        campoEMAIL.setText(funcionario.getEmail());
        campoTELEFONE.setText(funcionario.getTelefone());
        campoCELULAR.setText(funcionario.getCelular());
        campoNasc.setText(funcionario.getDataNascimento());
        CampoIDCARGOF.setText(Integer.toString(funcionario.getCargoId()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CadastroFActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("objetoDataFuncionario", Item);
                context.startActivity(intent);

            }
        });


        return view;
    }

}
