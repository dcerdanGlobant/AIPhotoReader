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
    @AccessibilityFocusState private var isDescriptionFocused: Bool
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
        .onAppear(perform: onAppearView)
        .accessibilityHidden(viewModel.description.isNil)
    }
}

// MARK: - Views
private extension ShareView {
    var navigationView: some View {
        HStack(spacing: 0) {
            Text(String.shareImage.title.localized)
                .fontWeight(.semibold)
                .font(.system(size: 18))
                .frame(maxWidth: .infinity)
                .accessibilityAddTraits(.isHeader)
        }
        .overlay(alignment: .leading) {
            Button {
                viewModel.dismiss()
            } label: {
                Text(String.shareImage.cancel.localized)
                    .font(.system(size: 17))
            }
        }
        .padding()
        .background(.ultraThinMaterial)
        .overlay(Divider(), alignment: .bottom)
    }
    
    var contentView: some View {
        ScrollView(.vertical) {
            VStack(alignment: .center, spacing: 20) {
                if let image = viewModel.image {
                    Image(uiImage: image)
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: UIScreen.main.bounds.width - 40)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        .accessibilityHidden(true)
                }
                if let description = viewModel.description {
                    Text(description)
                        .font(.body)
                        .multilineTextAlignment(.center)
                        .accessibilityFocused($isDescriptionFocused)
                        .onAppear(perform: onAppearDescription)
                } else {
                    Spacer(minLength: 150)
                    ProgressView()
                        .progressViewStyle(.circular)
                        .accessibilityHidden(true)
                }
                if viewModel.description.isNotNil {
                    Button {
                        viewModel.getMoreInfo()
                    } label: {
                        Text(String.shareImage.getMoreInfo.localized)
                            .font(.body)
                    }.buttonStyle(.bordered)
                }
                Spacer(minLength: 40)
            }.padding(20)
        }.scrollBounceBehavior(.basedOnSize)
    }
}

// MARK: Logic
private extension ShareView {
    func setupScreenWidth() {
        viewModel.setScreenWidth(UIScreen.main.bounds.width)
    }
}

// MARK: Bindings
private extension ShareView {
    func onAppearView() {
        setupScreenWidth()
        viewModel.viewAppeared()
    }
    
    func onAppearDescription() {
        isDescriptionFocused = true
    }
}
