//
//  ShareView.swift
//  iosApp
//
//  Created by Miguel Ferrer on 5/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

// MARK: - ShareView
struct ShareView: View {
    @StateObject private var viewModel: ShareViewModel
    
    init(with viewModel: ShareViewModel) {
        _viewModel = StateObject(wrappedValue: viewModel)
    }
    
    // MARK: - View
    var body: some View {
        VStack(spacing: 0) {
            navigationView
            contentView
        }
        .interactiveDismissDisabled()
        .onAppear { viewModel.loadData() }
    }
}

// MARK: - Views
private extension ShareView {
    var navigationView: some View {
        HStack(spacing: 0) {
            Button {
                viewModel.dismiss()
            } label: {
                HStack(spacing: 0) {
                    Text(verbatim: "Cancel")
                    Spacer()
                }.padding(.leading)
            }.frame(width: UIScreen.main.bounds.width / 3)
            Text(verbatim: "AiPhotoHeader")
                .font(.headline)
                .frame(width: UIScreen.main.bounds.width / 3)
            Spacer()
                .frame(width: UIScreen.main.bounds.width / 3)
        }
        .padding()
        .background(.ultraThinMaterial)
        .overlay(Divider(), alignment: .bottom)
    }
    
    var contentView: some View {
        ScrollView(.vertical) {
            VStack(alignment: .center, spacing: 20) {
                Image(uiImage: viewModel.image)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: UIScreen.main.bounds.width - 40)
                    .clipShape(RoundedRectangle(cornerRadius: 20))
                if let description = viewModel.description {
                    Text(description)
                }
                if viewModel.description.isNotNil {
                    Button {
                        viewModel.loadDescription()
                    } label: {
                        Text(verbatim: "Get More Info")
                    }.buttonStyle(.bordered)
                }
                Spacer()
            }.padding(20)
        }.scrollBounceBehavior(.basedOnSize)
    }
}
