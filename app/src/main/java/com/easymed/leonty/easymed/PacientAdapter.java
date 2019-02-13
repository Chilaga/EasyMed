package com.easymed.leonty.easymed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

class PacientAdapter extends RecyclerView.Adapter<PacientAdapter.ViewHolder> implements Filterable {

    private List<Pacient> pacients;
    private List<Pacient> pacientListFiltered;

    PacientAdapter(List<Pacient> pacients) {
        this.pacients = pacients;
        pacientListFiltered = new ArrayList<>(pacients);
    }

    @NonNull
    @Override
    public PacientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pacient_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PacientAdapter.ViewHolder viewHolder, int i) {
        Pacient currentItem = pacients.get(i);

        viewHolder.surname.setText(currentItem.getSurname());
        viewHolder.name_pacient.setText(currentItem.getName());
        viewHolder.patronymic.setText(currentItem.getPatronymic());
        viewHolder.birth.setText(currentItem.getBirthDate());
        viewHolder.diagnosis.setText(currentItem.getDiagnosis());
        viewHolder.id = currentItem.getId();
    }

    @Override
    public int getItemCount() {
        return pacients.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Pacient> filteredList = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0) {
                    filteredList.addAll(pacientListFiltered);
                } else {
                    String query = charSequence.toString();
                    String[] parts = query.split(" ");

                    outer: for (Pacient row : pacientListFiltered) {
                        List<String> rowFields = new ArrayList<>();
                        rowFields.add(row.getSurname().toLowerCase());
                        rowFields.add(row.getName().toLowerCase());
                        rowFields.add(row.getPatronymic().toLowerCase());
                        rowFields.add(row.getBirthDate());

                        for (int i = 0; i < parts.length; i++) {
                            String item = parts[i].toLowerCase().trim();
                            boolean equals = false;

                            if (i == parts.length-1) {
                                for (String field : rowFields) {
                                    if (item.length() <= field.length() && field.substring(0, item.length()).equals(item)) {
                                        equals = true;
                                        break;
                                    }
                                }
                            }
                            else {
                                for (String field : rowFields) {
                                    if (field.equals(item)) {
                                        equals = true;
                                        rowFields.remove(field);
                                        break;
                                    }
                                }
                            }
                            if (!equals) continue outer;
                        }
                        filteredList.add(row);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                pacients.clear();
                pacients.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView surname;
        TextView name_pacient;
        TextView patronymic;
        TextView birth;
        TextView diagnosis;
        int id;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            surname = itemView.findViewById(R.id.surname);
            name_pacient = itemView.findViewById(R.id.name);
            patronymic = itemView.findViewById(R.id.patronym);
            birth = itemView.findViewById(R.id.birthdate);
            diagnosis = itemView.findViewById(R.id.diagnosis);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, EditPacient.class);
                    Bundle b = new Bundle();
                    b.putInt("id", id);
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });
        }
    }
}
